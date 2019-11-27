package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.OfferBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.provider.SessionAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.OfferSqlRepository;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.offer.OfferByCourierIdSpecification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        HttpSession session = request.getSession();
        long id = (long) session.getAttribute(SessionAttributesNameProvider.ID);
        boolean status = false;
        try{
            if (command == CommandEnum.UPDATE_PROFILE_COURIER){
                Specification<OfferBean, PreparedStatement, Connection> specification = new OfferByCourierIdSpecification(id);
                Optional<List<OfferBean>> optionalOffers = repository.find(specification);
                status = optionalOffers.isPresent()? update(request, optionalOffers.get().get(0)) : add(request, id);
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
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
