package com.yaree.demo.utils;

public class User {

    private String url;
    private String login;
    private String password;
    private int timeout;

    private User() {}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public int getTimeout() {
        return timeout;
    }

    public static Builder builder() {
        return new User().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder setUrl(String url) {
            User.this.url = url;
            return this;
        }

        public Builder setTimeout(int timeout) {
            User.this.timeout = timeout;
            return this;
        }

        public User build() {
            return User.this;
        }

    }


}
