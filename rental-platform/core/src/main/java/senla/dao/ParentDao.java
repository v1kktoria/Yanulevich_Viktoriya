package senla.dao;

import senla.model.Identifiable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ParentDao<T extends Identifiable<ID>, ID extends Serializable>{
    T save (T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
}
