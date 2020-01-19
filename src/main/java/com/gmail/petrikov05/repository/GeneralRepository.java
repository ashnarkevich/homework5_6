package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    void dropTable(Connection connection) throws SQLException;

    void createTable(Connection connection) throws SQLException;

    void add(Connection connection, T t) throws SQLException;

}
