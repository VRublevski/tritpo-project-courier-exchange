package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.ActorBean;
import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.bean.UserBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.RoleEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.provider.RequestAttributesNameProvider;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.ImageSqlRepository;
import by.bsuir.exchange.specification.Specification;
import by.bsuir.exchange.specification.image.ImageByRoleIdSpecification;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ImageManager extends AbstractManager<ImageBean> implements CommandHandler {

    public ImageManager() throws ManagerInitializationException {
        try {
            this.repository = new ImageSqlRepository();
        } catch (RepositoryInitializationException e) {
            throw new ManagerInitializationException(e);
        }
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status = false;
        try{
            if (command == CommandEnum.DELETE_USER){
                UserBean user = (UserBean) request.getAttribute(RequestAttributesNameProvider.USER_ATTRIBUTE);
                ActorBean actor = (ActorBean) request.getAttribute(RequestAttributesNameProvider.ACTOR_ATTRIBUTE);
                String role = user.getRole();
                long id = actor.getId();
                Specification<ImageBean, PreparedStatement, Connection> specification = new ImageByRoleIdSpecification(role, id);
                Optional<List<ImageBean>> optionalImageBeans = repository.find(specification);
                if (optionalImageBeans.isPresent()){
                    ImageBean foundImage = optionalImageBeans.get().get(0);
                    foundImage.setArchival(true);
                    repository.update(foundImage);
                }
                status = true;
            }
        }catch (RepositoryOperationException e){
            throw new ManagerOperationException(e);
        }
        return status;
    }
}
