package com.example.myapplication.domain;

/**
 * 学院实体
 */
public class Dep {
    private int _id;
    private String name;

    public Dep(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dep{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }
}
