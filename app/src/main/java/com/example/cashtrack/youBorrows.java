package com.example.cashtrack;

import java.io.Serializable;

public class youBorrows implements Serializable {
    private int id;
    private String desc;
    private String name;
    private int cost;
    private String date;

    public youBorrows(int id, String desc, String name, int cost, String date) {
        this.id = id;
        this.desc = desc;
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

