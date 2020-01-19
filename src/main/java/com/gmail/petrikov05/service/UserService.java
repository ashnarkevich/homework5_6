package com.gmail.petrikov05.service;

import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.service.model.AddUserDTO;
import com.gmail.petrikov05.service.model.UserDTO;
import com.gmail.petrikov05.service.model.UserInGroupDTO;

public interface UserService {

    void dropTable() throws SQLException;

    void createTable() throws SQLException;

    UserDTO add(AddUserDTO addUserDTO) throws SQLException;

    void addGroup(String userGroup) throws SQLException;

    List<UserDTO> findAll() throws SQLException;

    List<UserInGroupDTO> findCountUserInGroup() throws SQLException;

    int deleteUserByMinAge(int minValueAge) throws SQLException;

    int updateUserWithParameter(int minValueAgeInRange, int maxValueAgeInRange, boolean isActive) throws SQLException;

}
