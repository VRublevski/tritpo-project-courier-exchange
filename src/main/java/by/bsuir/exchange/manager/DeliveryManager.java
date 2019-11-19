package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.DeliverySqlRepository;
import by.bsuir.exchange.repository.impl.SqlRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            default: {
                throw new ManagerOperationException("Unexpected command");
            }
        }
        return status;
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
}
