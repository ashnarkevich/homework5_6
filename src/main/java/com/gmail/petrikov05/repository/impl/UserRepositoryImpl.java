package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.repository.UserRepository;
import com.gmail.petrikov05.repository.model.User;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public int deleteUserByMinAge(Connection connection, int minValueAge) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE age < ?;")) {
            preparedStatement.setInt(1, minValueAge);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Integer> findUserWithParameter(Connection connection, int minValueAgeInRange, int maxValueAgeInRange, boolean isActive) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM user WHERE age >= ? && age <= ? && is_active = ?;"
        )) {
            preparedStatement.setInt(1, minValueAgeInRange);
            preparedStatement.setInt(2, maxValueAgeInRange);
            preparedStatement.setBoolean(3, isActive);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                ids.add(id);
            }
            return ids;
        }

    }

    @Override
    public int updateById(Connection connection, String ids) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            int affectedRows = statement.executeUpdate("UPDATE user SET is_active = false WHERE id IN (" + ids + ");");
            return affectedRows;
        }
    }

    @Override
    public void dropTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user;");
        }
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE user (" +
                    "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
                    "username VARCHAR(40) NOT NULL, " +
                    "password VARCHAR(40) NOT NULL, " +
                    "is_active TINYINT(1) NOT NULL, " +
                    "user_group_id INT(11) NOT NULL, " +
                    "age INT(11) NOT NULL, " +
                    "FOREIGN KEY (user_group_id) REFERENCES user_group(id) " +
                    ");");
        }

    }

    @Override
    public void add(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into user(username, password, is_active, user_group_id, age) " +
                        "values(?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
        )) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isActive());
            preparedStatement.setInt(4, user.getUserGroupId());
            preparedStatement.setInt(5, user.getAge());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM user;"
            );
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                userList.add(user);
            }
            return userList;
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return User.newBuilder()
                .id(resultSet.getInt("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .isActive(Boolean.getBoolean(resultSet.getString("is_active")))
                .userGroupId(resultSet.getInt("user_group_id"))
                .age(resultSet.getInt("age"))
                .build();
    }

}
