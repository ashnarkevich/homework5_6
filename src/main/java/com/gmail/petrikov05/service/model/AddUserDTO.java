package com.gmail.petrikov05.service.model;

public class AddUserDTO {

    private int id;
    private String username;
    private String password;
    private boolean isActive;
    private int userGroupId;
    private int age;
    private String address;
    private String telephone;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    private AddUserDTO(Builder builder) {
        id = builder.id;
        username = builder.username;
        password = builder.password;
        isActive = builder.isActive;
        userGroupId = builder.userGroupId;
        age = builder.age;
        address = builder.address;
        telephone = builder.telephone;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private int id;
        private String username;
        private String password;
        private boolean isActive;
        private int userGroupId;
        private int age;
        private String address;
        private String telephone;

        private Builder() {}

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder isActive(boolean val) {
            isActive = val;
            return this;
        }

        public Builder userGroupId(int val) {
            userGroupId = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder telephone(String val) {
            telephone = val;
            return this;
        }

        public AddUserDTO build() {
            return new AddUserDTO(this);
        }

    }

}
