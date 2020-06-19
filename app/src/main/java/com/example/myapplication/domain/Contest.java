package com.example.myapplication.domain;

public class Contest {
    private int contestId;
    private String contestName;
    private String contestIntroduction;
    private String contestTime;
    private String contestNote;

    public Contest(String contestName, String contestIntroduction, String contestTime, String contestNote) {
        this.contestName = contestName;
        this.contestIntroduction = contestIntroduction;
        this.contestTime = contestTime;
        this.contestNote = contestNote;
    }

    public Contest(int contestId, String contestName, String contestIntroduction, String contestTime, String contestNote) {
        this.contestId = contestId;
        this.contestName = contestName;
        this.contestIntroduction = contestIntroduction;
        this.contestTime = contestTime;
        this.contestNote = contestNote;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestIntroduction() {
        return contestIntroduction;
    }

    public void setContestIntroduction(String contestIntroduction) {
        this.contestIntroduction = contestIntroduction;
    }

    public String getContestTime() {
        return contestTime;
    }

    public void setContestTime(String contestTime) {
        this.contestTime = contestTime;
    }

    public String getContestNote() {
        return contestNote;
    }

    public void setContestNote(String contestNote) {
        this.contestNote = contestNote;
    }
}
