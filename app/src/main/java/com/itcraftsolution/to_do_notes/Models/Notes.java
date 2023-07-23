package com.itcraftsolution.to_do_notes.Models;

public class Notes {

    private int id;
    private String title, desc, date;
    private boolean pin;

    public Notes(int id, String title, String desc, String date, boolean pin) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.pin = pin;
    }

    public Notes(String title, String desc, String date, boolean pin) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }
}
