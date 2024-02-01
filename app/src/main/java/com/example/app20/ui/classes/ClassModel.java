package com.example.app20.ui.classes;
public class ClassModel {
    private int id;
    private String courseNum, prof, time;

    public ClassModel(int id, String courseNum, String prof, String time) {
        this.id = id;
        this.courseNum = courseNum;
        this.prof = prof;
        this.time = time;
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
