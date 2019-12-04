package by.bsuir.exchange.manager;

import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.repository.exception.RepositoryInitializationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.SqlRepository;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractManager<T> implements CommandHandler {
    SqlRepository<T> repository;

    public abstract boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException;

    public <T2> AbstractManager<T> combine(AbstractManager<T2> other) throws ManagerOperationException {
        SqlRepository<T> packed;
        try {
            packed = repository.pack(other.repository);
        } catch (RepositoryInitializationException e) {
            throw new ManagerOperationException(e);
        }
        AbstractManager<T> manager = new AbstractManager<>() {
            @Override
            public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
                return AbstractManager.this.handle(request, command) && other.handle(request, command);
            }

            @Deprecated
            public void closeManager() throws ManagerOperationException {
                other.closeManager();
                AbstractManager.this.closeManager();
            }
        };
        manager.repository = packed;
        return manager;
    }

    public void closeManager() throws ManagerOperationException {
        try {
            repository.closeRepository();
        } catch (RepositoryOperationException e) {
            throw new ManagerOperationException(e);
        }
    }
}
