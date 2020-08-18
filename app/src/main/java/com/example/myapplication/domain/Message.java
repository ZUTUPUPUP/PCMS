package com.example.myapplication.domain;

public class Message {
    private int _id; //编号
    private String userId; //用户id
    private String title; //标题
    private String content; //内容
    private String time; //时间
    private String vis; //是否发送过通知


    public Message() { }
    public Message(int _id, String userId, String title, String content, String time,String vis) {
        this._id = _id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.time = time;
        this.vis = vis;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    @Override
    public String toString() {
        return "Message{" +
                "_id=" + _id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", vis=" + vis +
                '}';
    }
}