package com.example.myapplication.domain;

/**
 * 用户信息类
 */
public class User {
    private String userName;//用户名
    private String passwd;//密码
    private String nickName;//用户昵称
    private String gender;//性别
    private String department;//学院
    private int status_id;//身份id

    public User(String userName, String passwd, String nickName, String gender, String department, int status_id) {
        this.userName = userName;
        this.passwd = passwd;
        this.nickName = nickName;
        this.gender = gender;
        this.department = department;
        this.status_id = status_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + userName + '\'' +
                ", paw='" + passwd + '\'' +
                ", nick='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", status_id=" + status_id +
                '}';
    }
}
