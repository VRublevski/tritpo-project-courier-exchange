package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.DeliverySqlRepository;
import by.bsuir.exchange.repository.impl.SqlRepository;
import by.bsuir.exchange.repository.specification.DeliveryByActorIdSpecification;
import by.bsuir.exchange.repository.specification.DeliveryByClientIdSpecification;
import by.bsuir.exchange.repository.specification.DeliveryByCourierIdSpecification;
import by.bsuir.exchange.repository.specification.Specification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DeliveryManager implements CommandHandler {
    private static DeliveryManager instance;

    public static DeliveryManager getInstance() throws ManagerInitializationException {
        if (instance == null){
            SqlRepository<DeliveryBean> repository;
            try {
                repository = new DeliverySqlRepository();
            } catch (RepositoryInitializationException e) {
                throw new ManagerInitializationException(e);
            }
            instance = new DeliveryManager(repository);
        }
        return instance;
    }

    private SqlRepository<DeliveryBean> repository;

    private DeliveryManager(SqlRepository<DeliveryBean> repository){
        this.repository = repository;
    }


    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerInitializationException, ManagerOperationException {
        boolean status;
        switch (command){
            case REQUEST_DELIVERY: {
                status = requestDelivery(request);
                break;
            }
            case GET_DELIVERIES: {
                status = getDeliveries(request);
                break;
            }
            case FINISH_DELIVERY: {
                status = finishDelivery(request);
                break;
            }
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
    }

    private boolean getDeliveries(HttpServletRequest request) throws ManagerOperationException {
        HttpSession session = request.getSession();
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        long id = (long) session.getAttribute(SessionAttributesNameProvider.ID);
        Specification<DeliveryBean, PreparedStatement, Connection> specification = role == RoleEnum.CLIENT ?
                                                new DeliveryByClientIdSpecification(id) :
                                                new DeliveryByCourierIdSpecification(id);
        List<DeliveryBean > deliveries = Collections.emptyList();
        try {
            Optional< List< DeliveryBean > > optionalDeliveries = repository.find(specification);
            if (optionalDeliveries.isPresent()){
                deliveries = optionalDeliveries.get();
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        request.setAttribute("lst", deliveries);
        return true;
    }

    private boolean requestDelivery(HttpServletRequest request) throws ManagerOperationException {
        HttpSession session = request.getSession();
        long clientId = (long) session.getAttribute(SessionAttributesNameProvider.ID);
        String courierIdString =  request.getParameter("courier");
        long courierId = Long.parseLong(courierIdString);
        DeliveryBean delivery = new DeliveryBean(clientId, false, courierId, false);
        boolean status;
        try {
            repository.add(delivery);
            status = true;
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }

    private boolean finishDelivery(HttpServletRequest request) throws ManagerOperationException {
        HttpSession session = request.getSession();
        RoleEnum role = (RoleEnum) session.getAttribute(SessionAttributesNameProvider.ROLE);
        long firstId = (long) session.getAttribute(SessionAttributesNameProvider.ID);
        String secondIdString = request.getParameter("delivery");
        long secondId = Long.parseLong(secondIdString);
        if (role == RoleEnum.COURIER){
            long tmp = firstId + secondId;
            firstId = tmp - firstId;
            secondId = tmp - firstId;
        }
        DeliveryByActorIdSpecification specification = new DeliveryByActorIdSpecification(firstId, secondId);
        DeliveryBean delivery;
        boolean status;
        try {
            Optional< List< DeliveryBean > > optionalDeliveries = repository.find(specification);
            if (optionalDeliveries.isPresent()){
                delivery = optionalDeliveries.get().get(0);
                if (role == RoleEnum.CLIENT) {
                    delivery.setClientFinished(true);
                } else {
                    delivery.setCourierFinished(true);
                }
                repository.update(delivery);
                status = true;
            }else{
                status = false;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }
}
