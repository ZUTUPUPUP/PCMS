package com.example.myapplication.domain;

/**
 * Bean
 * 学院
 */
public class Dep {


    /**
     * _id : 1
     * name : 计算机学院
     */

    private Integer _id;
    private String name;

    public Dep() {
    }

    public Dep(Integer _id, String name) {
        this._id = _id;
        this.name = name;
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
