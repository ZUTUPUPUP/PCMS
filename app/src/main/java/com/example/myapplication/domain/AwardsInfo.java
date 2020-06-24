package com.example.myapplication.domain;


/**
 * 获奖信息类
 */
public class AwardsInfo {
    private Integer _id;
    private String STNumber; //学号
    private String relName; //姓名
    private String className; //班级
    private String contestName; //比赛类型
    private String awardLevel;//获奖等级
    private String depName;

    public AwardsInfo(Integer _id, String STNumber, String relName, String className, String contestName, String awardLevel, String depName) {
        this._id = _id;
        this.STNumber = STNumber;
        this.relName = relName;
        this.className = className;
        this.contestName = contestName;
        this.awardLevel = awardLevel;
        this.depName = depName;
    }

    @Override
    public String toString() {
        return "AwardsInfo{" +
                "_id=" + _id +
                ", STNumber='" + STNumber + '\'' +
                ", relName='" + relName + '\'' +
                ", className='" + className + '\'' +
                ", contestName='" + contestName + '\'' +
                ", awardLevel='" + awardLevel + '\'' +
                ", depName='" + depName + '\'' +
                '}';
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getSTNumber() {
        return STNumber;
    }

    public void setSTNumber(String STNumber) {
        this.STNumber = STNumber;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
