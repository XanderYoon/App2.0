package com.example.app20.ui.classes;
public class ClassModel {
    private int id, hour, minute;
    private String courseNum, prof;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ClassModel(int id, int hour, int minute, String courseNum, String prof) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.courseNum = courseNum;
        this.prof = prof;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseNum() {
        return courseNum;
    }
    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }
    public String getProf() {
        return prof;
    }
    public void setProf(String prof) {
        this.prof = prof;
    }
}
