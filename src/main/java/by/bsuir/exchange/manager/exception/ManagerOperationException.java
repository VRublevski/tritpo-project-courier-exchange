package by.bsuir.exchange.manager.exception;

public class ManagerOperationException extends Exception{
    public ManagerOperationException(Throwable t){
        super(t);
    }

    public ManagerOperationException(String msg){
        super(msg);
    }
}
