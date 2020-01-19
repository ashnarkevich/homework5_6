package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.petrikov05.repository.UserInformationRepository;
import com.gmail.petrikov05.repository.model.User;

public class UserInformationRepositoryImpl extends GeneralRepositoryImpl<User> implements UserInformationRepository {

    private static UserInformationRepository instance;

    private UserInformationRepositoryImpl() {
    }

    public static UserInformationRepository getInstance() {
        if (instance == null) {
            instance = new UserInformationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void dropTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user_information;");
        }
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE user_information (" +
                    "user_id INT(11) PRIMARY KEY, " +
                    "address VARCHAR(100) NOT NULL, " +
                    "telephone VARCHAR(40), " +
                    "FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE cascade " +
                    ");");
        }
    }

    @Override
    public void add(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into user_information (user_id, address, telephone) " +
                        "values(?, ?, ?);"
        )) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getAddress());
            preparedStatement.setString(3, user.getTelephone());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user_information failed, no rows affected");
            }
        }

    }

}
