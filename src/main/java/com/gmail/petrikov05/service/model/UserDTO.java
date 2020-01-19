package com.gmail.petrikov05.service.model;

public class UserDTO {

    private String username;
    private String password;
    private boolean isActive;
    private int age;

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", age=" + age +
                '}';
    }

    public UserDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private UserDTO(Builder builder) {
        username = builder.username;
        password = builder.password;
        isActive = builder.isActive;
        age = builder.age;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private String username;
        private String password;
        private boolean isActive;
        private int age;

        private Builder() {}

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

        public Builder age(int val) {
            age = val;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }

    }

}
