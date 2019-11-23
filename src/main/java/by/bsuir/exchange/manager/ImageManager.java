package by.bsuir.exchange.manager;

import by.bsuir.exchange.bean.ImageBean;
import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerInitializationException;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.ImageSqlRepository;
import by.bsuir.exchange.repository.impl.SqlRepository;
import by.bsuir.exchange.repository.specification.ImageByIdSpecification;
import by.bsuir.exchange.repository.specification.Specification;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class ImageManager implements CommandHandler {

    private static ImageManager instance;

    public static ImageManager getInstance() throws ManagerInitializationException {
        if (instance == null){
            SqlRepository<ImageBean> repository;
            try {
                repository = new ImageSqlRepository();
            } catch (RepositoryInitializationException e) {
                throw new ManagerInitializationException(e);
            }
            instance = new ImageManager(repository);
        }
        return instance;
    }

    private SqlRepository<ImageBean> repository;

    private ImageManager(SqlRepository<ImageBean> repository) {
        this.repository = repository;
    }

    @Override
    public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
        boolean status = false;
        if (command == CommandEnum.GET_IMAGE){
            status = getImage(request);
        }
        return status;
    }

    private boolean getImage(HttpServletRequest request) throws ManagerOperationException {
        String idString = request.getParameter("id");
        long id = Long.parseLong(idString);
        Specification<ImageBean, PreparedStatement, Connection> specification = new ImageByIdSpecification(id);
        boolean status = false;
        try {
            Optional<List< ImageBean >> imagesOptional = repository.find(specification);
            if (imagesOptional.isPresent()){
                ImageBean image = imagesOptional.get().get(0);
                request.setAttribute("image", image);
                status = true;
            }
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
        return status;
    }
}
