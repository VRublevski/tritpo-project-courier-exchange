package by.bsuir.exchange.manager;

import by.bsuir.exchange.chain.CommandHandler;
import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.manager.exception.ManagerOperationException;
import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.impl.SqlRepository;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractManager<T> implements CommandHandler {
    SqlRepository<T> repository;

    public abstract boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException;

    public <T2> AbstractManager<T> combine(AbstractManager<T2> other) {
        SqlRepository<T> packed;
        packed = repository.pack(other.repository);
        AbstractManager<T> manager = new AbstractManager<>() {
            @Override
            public boolean handle(HttpServletRequest request, CommandEnum command) throws ManagerOperationException {
                boolean status = AbstractManager.this.handle(request, command);
                status &= other.handle(request, command);
                return status;
            }

            @Deprecated
            public void closeManager() throws ManagerOperationException {
                AbstractManager.this.closeManager();
                other.closeManager();
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