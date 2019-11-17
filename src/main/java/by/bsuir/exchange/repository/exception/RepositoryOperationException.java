package by.bsuir.exchange.repository.exception;

public class RepositoryOperationException extends Exception {
    public RepositoryOperationException(Throwable t){
        super(t);
    }
    public RepositoryOperationException(String msg){super(msg);}
}
