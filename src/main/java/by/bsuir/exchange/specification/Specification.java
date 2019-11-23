package by.bsuir.exchange.specification;

public interface Specification<T, R, H> {
    boolean specify(T entity);

    default R specify() throws Exception{
        throw new UnsupportedOperationException();
    }

    default void setHelperObject(H obj){
        throw new UnsupportedOperationException();
    }
}
