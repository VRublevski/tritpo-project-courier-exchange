package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.bean.DeliveryBean;
import by.bsuir.exchange.bean.OfferBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.OfferSqlRepository;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.offer.OfferAllSpecification;
import by.bsuir.exchange.specification.offer.OfferByCourierIdSpecification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OfferManager extends AbstractManager<OfferBean> implements CommandHandler {

    public OfferManager() throws ManagerInitializationException {
        try {
            this.repository = new OfferSqlRepository();
        } catch (RepositoryInitializationException e) {
            throw new ManagerInitializationException(e);
        }

    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status;
        try{
            switch (command){
                case DELETE_USER: {
                    status = deleteUser(request);
                    break;
                }
                case UPDATE_PROFILE_COURIER:{
                    HttpSession session = request.getSession();
                    long id = (long) session.getAttribute(SessionAttributesNameProvider.ID);
                    Specification<OfferBean, PreparedStatement, Connection> specification = new OfferByCourierIdSpecification(id);
                    Optional<List<OfferBean>> optionalOffers = repository.find(specification);
                    status = optionalOffers.isPresent()? update(request, optionalOffers.get().get(0)) : add(request, id);
                    break;
                }
                case GET_OFFERS: {
                    status = getOffers(request);
                    break;
                }
                case REQUEST_DELIVERY: {
                    status = requestDelivery(request);
                    break;
                }
                case FINISH_DELIVERY: {
                    status = finishDelivery(request);
                    break;
                }
                default: throw new ManagerOperationException("Unsupported command");
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }

    private boolean deleteUser(HttpServletRequest request) throws RepositoryOperationException {
        ActorBean actor = (ActorBean) request.getAttribute(RequestAttributesNameProvider.ACTOR_ATTRIBUTE);
        long id = actor.getId();
        Specification<OfferBean, PreparedStatement, Connection> specification = new OfferByCourierIdSpecification(id);

        Optional< List<OfferBean>> optionalOffers = repository.find(specification);
        if (optionalOffers.isPresent()){
            List<OfferBean> offers = optionalOffers.get();
            for (OfferBean foundOffer : offers){
                foundOffer = optionalOffers.get().get(0);
                foundOffer.setArchival(true);
                repository.update(foundOffer);
            }
        }
        return true;
    }

    private boolean finishDelivery(HttpServletRequest request) throws RepositoryOperationException {
        DeliveryBean delivery = (DeliveryBean) request.getAttribute(RequestAttributesNameProvider.DELIVERY_ATTRIBUTE);
        Specification<OfferBean, PreparedStatement, Connection> specification =
                new OfferByCourierIdSpecification(delivery.getCourierId());
        Optional< List<OfferBean> > optionalOffers = repository.find(specification);
        boolean status;
        if (optionalOffers.isPresent()){
            OfferBean offer = optionalOffers.get().get(0);
            request.setAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE, offer);
            status = true;
        }else {
            status = false;
        }
        return status;
    }

    private boolean requestDelivery(HttpServletRequest request) throws RepositoryOperationException {
        DeliveryBean delivery = (DeliveryBean) request.getAttribute(RequestAttributesNameProvider.DELIVERY_ATTRIBUTE);
        long courierId = delivery.getCourierId();
        Specification<OfferBean, PreparedStatement, Connection> specification = new OfferByCourierIdSpecification(courierId);
        Optional<List<OfferBean>> optionalOffers = repository.find(specification);
        boolean status = false;
        if (optionalOffers.isPresent()){
            OfferBean offer = optionalOffers.get().get(0);
            request.setAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE, offer);
            status = true;
        }
        return status;
    }

    private boolean getOffers(HttpServletRequest request) throws RepositoryOperationException {
        Specification<OfferBean, PreparedStatement, Connection> specification = new OfferAllSpecification();
        Optional< List<OfferBean> > optionalOffers = repository.find(specification);
        List<OfferBean> result = optionalOffers.orElse(Collections.emptyList());
        request.setAttribute(RequestAttributesNameProvider.OFFER_LIST_ATTRIBUTE, result);
        return true;
    }

    private boolean add(HttpServletRequest request, long courierId) throws RepositoryOperationException {
        OfferBean actualBean = (OfferBean) request.getAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE);
        actualBean.setCourierId(courierId);
        repository.add(actualBean);
        request.setAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE, actualBean);
        return true;
    }

    private boolean update(HttpServletRequest request, OfferBean foundBean) throws RepositoryOperationException {
        OfferBean actualBean = (OfferBean) request.getAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE);
        actualBean.setId(foundBean.getId());
        repository.update(actualBean);
        request.setAttribute(RequestAttributesNameProvider.OFFER_ATTRIBUTE, actualBean);
        return true;
    }
}
