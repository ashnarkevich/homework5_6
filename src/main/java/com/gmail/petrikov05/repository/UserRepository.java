package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    int deleteUserByMinAge(Connection connection, int minValueAge) throws SQLException;

    List<Integer> findUserWithParameter(Connection connection, int minValueAgeInRange, int maxValueAgeInRange, boolean isActive) throws SQLException;

    int updateById(Connection connection, String ids) throws SQLException;

    List<User> findAll(Connection connection) throws SQLException;

}
