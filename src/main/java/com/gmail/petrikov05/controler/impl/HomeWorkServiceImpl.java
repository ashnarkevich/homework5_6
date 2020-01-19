package com.gmail.petrikov05.controler.impl;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.controler.HomeWorkService;
import com.gmail.petrikov05.service.UserService;
import com.gmail.petrikov05.service.impl.UserServiceImpl;
import com.gmail.petrikov05.service.model.AddUserDTO;
import com.gmail.petrikov05.service.model.UserDTO;
import com.gmail.petrikov05.service.model.UserGroupEnum;
import com.gmail.petrikov05.service.model.UserInGroupDTO;
import com.gmail.petrikov05.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomeWorkServiceImpl implements HomeWorkService {

    private final static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    private static HomeWorkService instance;

    private HomeWorkServiceImpl() {
    }

    public static HomeWorkService getInstance() {
        if (instance == null) {
            instance = new HomeWorkServiceImpl();
        }
        return instance;
    }

    @Override
    public void runTaskA() throws SQLException {
        logger.info("+++++ run Task A +++++");
        userService.dropTable();

    }

    @Override
    public void runTaskB() throws SQLException {
        logger.info("+++++ run Task B +++++");
        userService.createTable();

    }

    @Override
    public void runTaskC() throws SQLException {
        logger.info("+++++ run Task C +++++");
        for (UserGroupEnum userGroupEnum : UserGroupEnum.values()) {
            userService.addGroup(userGroupEnum.toString());
        }

    }

    @Override
    public void runTaskD() throws SQLException {
        logger.info("+++++ run Task D +++++");
        int countOfUser = 30;
        int minAge = 25;
        int maxAge = 35;
        for (int i = 0; i < countOfUser; i++) {
            AddUserDTO addUserDTO = getAddUserDTO(minAge, maxAge);
            UserDTO userDTO = userService.add(addUserDTO);
        }

    }

    private AddUserDTO getAddUserDTO(int minAge, int maxAge) {
        int id = RandomUtil.getInt();
        String userName = "Name" + RandomUtil.getInt();
        String password = userName + "pass";
        boolean isActive = RandomUtil.getBoolean();
        int userGroupId = getUserGroupId();
        int age = RandomUtil.getIntFromRange(minAge, maxAge);
        String address = "address" + RandomUtil.getInt();
        String telephone = "telephone" + RandomUtil.getInt();

        return AddUserDTO.newBuilder()
                .id(id)
                .username(userName)
                .password(password)
                .isActive(isActive)
                .userGroupId(userGroupId)
                .age(age)
                .address(address)
                .telephone(telephone)
                .build();
    }

    private int getUserGroupId() {
        return RandomUtil.getIntFromRange(1, UserGroupEnum.values().length);
    }

    @Override
    public void runTaskE() throws SQLException {
        logger.info("+++++ run Task E +++++");
        List<UserDTO> userDTOList = userService.findAll();
        userDTOList.forEach(logger::info);

    }

    @Override
    public void runTaskF() throws SQLException {
        logger.info("+++++ run Task F +++++");
        List<UserInGroupDTO> userInGroupDTOList = userService.findCountUserInGroup();
        userInGroupDTOList.forEach(logger::info);
    }

    @Override
    public void runTaskG() throws SQLException {
        logger.info("+++++ run Task G +++++");
        int minValueAge = 27;
        int result = userService.deleteUserByMinAge(minValueAge);
        logger.info("The number of removed users with an age of less than " + minValueAge + ": " + result);

    }

    @Override
    public void runTaskH() throws SQLException {
        logger.info("+++++ run Task H +++++");
        int minValueAgeInRange = 30;
        int maxValueAgeInRange = 33;
        boolean isActive = true;
        int affectedRows = userService.updateUserWithParameter(minValueAgeInRange, maxValueAgeInRange, isActive);
        logger.info("Number deleted users with parameters: " + affectedRows);

    }

}
