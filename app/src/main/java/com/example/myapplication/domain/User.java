package com.example.myapplication.domain;

/**
 * 用户信息类
 */
public class User {
    private Integer _id;
    private String userName;//用户名
    private String passwd;//密码
    private String nickName;//用户昵称
    private String gender;//性别
    private int department_id;//学院
    private int status_id;//身份id


    private Dep dep;
    private Status status;

    public User() {
    }

    public User(Integer _id, String userName, String passwd, String nickName, String gender, int department_id, int status_id) {
        this._id = _id;
        this.userName = userName;
        this.passwd = passwd;
        this.nickName = nickName;
        this.gender = gender;
        this.department_id = department_id;
        this.status_id = status_id;
    }

    public Dep getDep() {
        return dep;
    }

    public void setDep(Dep dep) {
        this.dep = dep;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", department_id=" + department_id +
                ", status_id=" + status_id +
                ", dep=" + dep +
                ", status=" + status +
                '}';
    }
}
