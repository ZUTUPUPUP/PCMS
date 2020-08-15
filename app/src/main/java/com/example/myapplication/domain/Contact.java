package com.example.myapplication.domain;
/*
*一条聊天记录
 */
public class Contact {
    private Integer _id; //聊天记录的id
    private String timestamp; //时间戳
    private String senderId;//发送者
    private String receiverId;//接收者
    private String mas; //消息内容

    public Contact(Integer _id, String timestamp, String senderId, String receiverId, String mas) {
        this._id = _id;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.mas = mas;
    }

    public Contact() {

    }

    @Override
    public String toString() {
        return "Contact{" +
                "_id=" + _id +
                ", timestamp='" + timestamp + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", mas='" + mas + '\'' +
                '}';
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMas() {
        return mas;
    }

    public void setMas(String mas) {
        this.mas = mas;
    }
}
