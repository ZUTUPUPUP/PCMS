package com.example.myapplication.domain;

import java.util.Date;

public class Contest {
    private int id;
    private String name;
    private String introduction;
    private Date startTime;
    private Date endTime;

    public Contest(int id, String name, String introduction, Date startTime, Date endTime) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
