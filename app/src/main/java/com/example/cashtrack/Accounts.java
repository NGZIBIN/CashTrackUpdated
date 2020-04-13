package com.example.cashtrack;

import java.io.Serializable;

public class Accounts implements Serializable {
    private int id;
    private String name;
    private int num;
    private String pass;

    public Accounts(int id, String name, int num, String pass) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.pass = pass;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
