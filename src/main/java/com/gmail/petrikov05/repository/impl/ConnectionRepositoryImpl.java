package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static com.gmail.petrikov05.repository.constant.ConnectionConstant.CACHE_PREP_STMTS;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.DATABASE_PASSWORD;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.DATABASE_URL;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.DATABASE_USERNAME;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.PREP_STMT_CACHE_SIZE;
import static com.gmail.petrikov05.repository.constant.ConnectionConstant.PREP_STMT_CACHE_SQL_LIMIT;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static ConnectionRepository instance;

    public ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    private static HikariDataSource hikariDataSource;

    @Override
    public Connection getConnection() throws SQLException {
        if (hikariDataSource == null) {
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty(CACHE_PREP_STMTS, propertyUtil.getProperty(CACHE_PREP_STMTS));
            config.addDataSourceProperty(PREP_STMT_CACHE_SIZE, propertyUtil.getProperty(PREP_STMT_CACHE_SIZE));
            config.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT, propertyUtil.getProperty(PREP_STMT_CACHE_SQL_LIMIT));
            hikariDataSource = new HikariDataSource(config);
        }
        return hikariDataSource.getConnection();
    }

}
