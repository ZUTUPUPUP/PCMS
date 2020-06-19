package com.example.myapplication.domain;


/**
 * 获奖信息类
 */
public class AwardsInfo {
    private String userId; //学号
    private String userName; //姓名
    private String college; //专业
    private String competitionType; //比赛类型
    private String awardLevel;//获奖等级

    public AwardsInfo(){

    }

    public AwardsInfo(String userId, String userName, String college, String competitionType, String awardLevel) {
        this.userId = userId;
        this.userName = userName;
        this.college = college;
        this.competitionType = competitionType;
        this.awardLevel = awardLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    @Override
    public String toString() {
        return "AwardsInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", college='" + college + '\'' +
                ", competitionType='" + competitionType + '\'' +
                ", awardLevel='" + awardLevel + '\'' +
                '}';
    }
}
