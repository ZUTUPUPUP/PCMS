package com.example.myapplication.domain;

public class Status {
    private Integer _id;
    private String name;

    @Override
    public String toString() {
        return "Status{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
