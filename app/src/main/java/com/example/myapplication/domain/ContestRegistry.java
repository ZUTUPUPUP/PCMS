package com.example.myapplication.domain;

public class ContestRegistry {
    private Integer _id;
    private int contestId;
    private int STNumberId;
    private int depId;
    private String ClassAndGrade;
    private String gender;
    private String email;
    private String relName;

    private User user;
    private Contest contest;

    public ContestRegistry() {
    }

    public ContestRegistry(Integer _id, int contestId, int STNumberId, int depId, String classAndGrade, String gender, String email, String relName) {
        this._id = _id;
        this.contestId = contestId;
        this.STNumberId = STNumberId;
        this.depId = depId;
        ClassAndGrade = classAndGrade;
        this.gender = gender;
        this.email = email;
        this.relName = relName;
    }

    @Override
    public String toString() {
        return "ContestRegistry{" +
                "_id=" + _id +
                ", contestId=" + contestId +
                ", STNumberId=" + STNumberId +
                ", depId=" + depId +
                ", ClassAndGrade='" + ClassAndGrade + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", relName='" + relName + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }


    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
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

    public int getDepId() { return depId; }

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

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }
}
