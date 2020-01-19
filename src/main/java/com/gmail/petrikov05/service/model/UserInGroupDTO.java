package com.gmail.petrikov05.service.model;

public class UserInGroupDTO {

    private UserGroupEnum userGroupEnum;
    private int countOfUser;

    public UserInGroupDTO(UserGroupEnum userGroupEnum, int countOfUser) {
        this.userGroupEnum = userGroupEnum;
        this.countOfUser = countOfUser;
    }

    @Override
    public String toString() {
        return "UserInGroupDTO{" +
                "userGroupEnum=" + userGroupEnum +
                ", countOfUser=" + countOfUser +
                '}';
    }

}
