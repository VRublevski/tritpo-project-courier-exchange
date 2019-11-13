package by.bsuir.exchange.bean;

import by.bsuir.exchange.entity.RoleEnum;

public class UserBean {
    private String login;
    private String password;
    private RoleEnum role;

    public UserBean(String login, String password, RoleEnum role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
