package com.example.example.entity;

import java.util.List;

public class UserList {
    private String code;
    private String msg;
    private String obj;
    private List<User> list;

    public UserList() {
    }

    public UserList(String code, String msg, String obj, List<User> list) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", obj='" + obj + '\'' +
                ", list=" + list +
                '}';
    }
}
