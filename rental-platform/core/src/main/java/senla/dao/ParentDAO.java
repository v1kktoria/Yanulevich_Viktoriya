package senla.dao;

import java.util.List;

public interface ParentDAO <T, ID>{
    T create (T entity);
    T getByParam(Object param);
    List<T> getAll();
    void updateById(ID id, T entity);
    void deleteById(ID id);

}
