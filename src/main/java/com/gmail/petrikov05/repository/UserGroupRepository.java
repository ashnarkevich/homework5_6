package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.model.UserInGroup;

public interface UserGroupRepository extends GeneralRepository<String> {

    List<UserInGroup> getCountUserInGroup(Connection connection) throws SQLException;

}
