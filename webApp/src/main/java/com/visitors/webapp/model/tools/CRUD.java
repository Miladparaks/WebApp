package com.visitors.webapp.model.tools;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    T save(T t) throws Exception;
    T edit(T t) throws Exception;
    T remove(int id) throws Exception;
    List<T> findAll() throws Exception;
    T findById(int id) throws Exception;

}
