package com.example.myapplication.domain;

public class ContestRegistryMessage {
        private int _id;
        private int contestId;
        private int STNumberId;
        private int depId;
        private String ClassAndGrade;
        private String gender;
        private String email;
        private String contestName;//比赛名称
        private String userName;//学号
        private String name;//学院
        private String relName;

    public ContestRegistryMessage(int _id, int contestId, int STNumberId, int depId, String classAndGrade, String gender, String email, String contestName, String userName, String name, String relName) {
        this._id = _id;
        this.contestId = contestId;
        this.STNumberId = STNumberId;
        this.depId = depId;
        ClassAndGrade = classAndGrade;
        this.gender = gender;
        this.email = email;
        this.contestName = contestName;
        this.userName = userName;
        this.name = name;
        this.relName = relName;
    }

    @Override
    public String toString() {
        return "ContestRegistryMessage{" +
                "_id=" + _id +
                ", contestId=" + contestId +
                ", STNumberId=" + STNumberId +
                ", depId=" + depId +
                ", ClassAndGrade='" + ClassAndGrade + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", contestName='" + contestName + '\'' +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", relName='" + relName + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getSTNumberId() {
        return STNumberId;
    }

    public void setSTNumberId(int STNumberId) {
        this.STNumberId = STNumberId;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getClassAndGrade() {
        return ClassAndGrade;
    }

    public void setClassAndGrade(String classAndGrade) {
        ClassAndGrade = classAndGrade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }
}
