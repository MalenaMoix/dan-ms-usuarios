package jms.dan.usuarios.model;

public class User {
    private Integer id;
    private String user;
    private String password;
    private UserType userType;

    public User() {}

    public User(Integer id, String user, String password, UserType userType) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
