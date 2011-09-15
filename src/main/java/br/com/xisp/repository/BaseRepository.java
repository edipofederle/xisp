package br.com.xisp.repository;

import java.sql.SQLException;

public interface BaseRepository<T> {
	T find(String name);
    void add(T t);
    void update(T t);
    void remove(T t) throws SQLException, Exception;
}