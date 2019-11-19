package by.bsuir.exchange.repository;

import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface Repository<T, R, H> {
    void add(T entity) throws RepositoryOperationException;
    Optional< List<T> > find(Specification<T, R, H> specification) throws RepositoryOperationException;
}
