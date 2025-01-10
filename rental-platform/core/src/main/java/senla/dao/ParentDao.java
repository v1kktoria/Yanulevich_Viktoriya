package senla.dao;

import senla.model.Identifiable;

import java.io.Serializable;
import java.util.List;

public interface ParentDao<T extends Identifiable<ID>, ID extends Serializable>{
    T save (T entity);
    T findById(ID id);
    List<T> findAll();
    void update(T entity);
    void deleteById(ID id);
}
