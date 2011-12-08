package br.com.xisp.repository;

import java.sql.SQLException;

public interface BaseRepository<T> {
	T find(Long id);
    void add(T t) throws SQLException, Exception;
    void update(T t);
    void remove(T t) throws SQLException, Exception;
}