package by.bsuir.exchange.repository;

import by.bsuir.exchange.repository.exception.RepositoryOperationException;
import by.bsuir.exchange.repository.specification.Specification;

import java.util.Optional;

public interface Repository<T> {
    Optional<T> find(Specification specification) throws RepositoryOperationException;
}
