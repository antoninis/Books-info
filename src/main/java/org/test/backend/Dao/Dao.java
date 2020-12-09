package org.test.backend.Dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T getById(Long id);
    void delete(T object);
    void save(T object);
    void change(T object);
//    void setDBManager(DBManager manager);
}
