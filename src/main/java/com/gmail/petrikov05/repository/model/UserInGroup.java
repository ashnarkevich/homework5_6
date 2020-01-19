package com.gmail.petrikov05.repository.model;

import com.gmail.petrikov05.service.model.UserGroupEnum;

public class UserInGroup {

    private UserGroupEnum userGroup;
    private int count;

    public UserInGroup(UserGroupEnum userGroup, int count) {
        this.userGroup = userGroup;
        this.count = count;
    }

    public UserGroupEnum getUserGroup() {
        return userGroup;
    }

    public int getCount() {
        return count;
    }

}
