package com.gmail.petrikov05.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.UserGroupRepository;
import com.gmail.petrikov05.repository.UserInformationRepository;
import com.gmail.petrikov05.repository.UserRepository;
import com.gmail.petrikov05.repository.impl.ConnectionRepositoryImpl;
import com.gmail.petrikov05.repository.impl.UserGroupRepositoryImpl;
import com.gmail.petrikov05.repository.impl.UserInformationRepositoryImpl;
import com.gmail.petrikov05.repository.impl.UserRepositoryImpl;
import com.gmail.petrikov05.repository.model.User;
import com.gmail.petrikov05.repository.model.UserInGroup;
import com.gmail.petrikov05.service.UserService;
import com.gmail.petrikov05.service.model.AddUserDTO;
import com.gmail.petrikov05.service.model.UserDTO;
import com.gmail.petrikov05.service.model.UserGroupEnum;
import com.gmail.petrikov05.service.model.UserInGroupDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserGroupRepository userGroupRepository = UserGroupRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private static UserService instance;

    public UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void dropTable() throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.dropTable(connection);
                userRepository.dropTable(connection);
                userGroupRepository.dropTable(connection);

                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Delete table failed");
        }

    }

    @Override
    public void createTable() throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupRepository.createTable(connection);
                userRepository.createTable(connection);
                userInformationRepository.createTable(connection);
                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Create table failed");
        }

    }

    @Override
    public UserDTO add(AddUserDTO addUserDTO) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit((false));
            try {
                User user = convertAddUserDTOToUser(addUserDTO);
                userRepository.add(connection, user);
                userInformationRepository.add(connection, user);
                UserDTO userDTO = convertUserToUserDTO(user);
                connection.commit();
                return userDTO;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Create table failed");
        }
        return null;
    }

    private User convertAddUserDTOToUser(AddUserDTO addUserDTO) {
        return User.newBuilder()
                .username(addUserDTO.getUsername())
                .password(addUserDTO.getPassword())
                .isActive(addUserDTO.isActive())
                .userGroupId(addUserDTO.getUserGroupId())
                .age(addUserDTO.getAge())
                .address(addUserDTO.getAddress())
                .telephone(addUserDTO.getTelephone())
                .build();
    }

    private UserDTO convertUserToUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getAge());
    }

    @Override
    public void addGroup(String userGroup) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userGroupRepository.add(connection, userGroup);
                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Add user_group failed");
        }
    }

    @Override
    public List<UserDTO> findAll() throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> userList = userRepository.findAll(connection);
                List<UserDTO> userDTOList = convertUserListToUserDTOList(userList);
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Find users failed");
        }

        return Collections.emptyList();
    }

    @Override
    public List<UserInGroupDTO> findCountUserInGroup() throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<UserInGroup> userInGroupList = userGroupRepository.getCountUserInGroup(connection);
                List<UserInGroupDTO> userInGroupDTOList = convertUserInGroupListToUserInGroupDTO(userInGroupList);
                connection.commit();
                return userInGroupDTOList;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Find group failed");
        }
        return Collections.emptyList();
    }

    @Override
    public int deleteUserByMinAge(int minValueAge) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int countOfRows = userRepository.deleteUserByMinAge(connection, minValueAge);
                connection.commit();
                return countOfRows;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Delete users failed");
        }
        return 0;
    }

    @Override
    public int updateUserWithParameter(int minValueAgeInRange, int maxValueAgeInRange, boolean isActive) throws SQLException {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Integer> ids = userRepository.findUserWithParameter(connection, minValueAgeInRange, maxValueAgeInRange, isActive);
                String strOfIds = ids.stream().map(String::valueOf).collect(Collectors.joining(", "));
                int result = userRepository.updateById(connection, strOfIds);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException("Update users failed");
        }
        return minValueAgeInRange;
    }

    private List<UserInGroupDTO> convertUserInGroupListToUserInGroupDTO(List<UserInGroup> userInGroupList) {
        List<UserInGroupDTO> userInGroupListDTO = new ArrayList<>();
        for (UserInGroup userInGroup : userInGroupList) {
            UserGroupEnum name = userInGroup.getUserGroup();
            int count = userInGroup.getCount();
            userInGroupListDTO.add(new UserInGroupDTO(name, count));
        }
        return userInGroupListDTO;
    }

    private List<UserDTO> convertUserListToUserDTOList(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>(userList.size());
        for (User user : userList) {
            UserDTO userDTO = UserDTO.newBuilder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .isActive(user.isActive())
                    .age(user.getAge())
                    .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

}
