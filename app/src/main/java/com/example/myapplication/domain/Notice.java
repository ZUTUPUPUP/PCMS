package com.example.myapplication.domain;

public class Notice {
    private int _id; //编号
    private String title; //标题
    private String content; //内容
    private String time; //时间

    public Notice(){

    }
    public Notice(int _id, String title, String content, String time) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
