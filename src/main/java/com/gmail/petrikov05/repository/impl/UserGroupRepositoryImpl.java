package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.petrikov05.repository.UserGroupRepository;
import com.gmail.petrikov05.repository.model.User;
import com.gmail.petrikov05.repository.model.UserInGroup;
import com.gmail.petrikov05.service.model.UserGroupEnum;

public class UserGroupRepositoryImpl extends GeneralRepositoryImpl<String> implements UserGroupRepository {

    private static UserGroupRepository instance;

    private UserGroupRepositoryImpl() {
    }

    public static UserGroupRepository getInstance() {
        if (instance == null) {
            instance = new UserGroupRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void dropTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user_group;");
        }

    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE user_group (" +
                    "id INT(11) PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL" +
                    ");");
        }
    }

    @Override
    public void add(Connection connection, String userGroup) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user_group(name) VALUES(?);"
        )) {
            preparedStatement.setString(1, userGroup);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<UserInGroup> getCountUserInGroup(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT g.name AS name, COUNT(u.user_group_id) AS count from user_group g JOIN user u ON g.id = u.user_group_id GROUP BY g.name;";
            boolean affectedRows = statement.execute(query);
            if (affectedRows) {
                ResultSet resultSet = statement.executeQuery(query);
                List<UserInGroup> userInGroupList = new ArrayList<>();
                while (resultSet.next()) {
                    userInGroupList.add(getUserInGroup(resultSet));
                }
                return userInGroupList;
            } else {
                return Collections.emptyList();
            }
        }
    }

    private UserInGroup getUserInGroup(ResultSet resultSet) throws SQLException {
        String nameGroup = resultSet.getString("name");
        UserGroupEnum name = UserGroupEnum.valueOf(nameGroup.toUpperCase());
        int count = resultSet.getInt("count");
        return new UserInGroup(name, count);
    }

}
