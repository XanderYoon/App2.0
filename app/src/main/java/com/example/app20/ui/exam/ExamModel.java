package com.example.app20.ui.exam;

public class ExamModel {
    private int id;
    private int day;
    private int month;
    private int hour;

    public ExamModel(int id, int day, int month, int hour, int minute, String exam, String location) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.exam = exam;
        this.location = location;
    }

    private int minute;
    private String exam, location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
}

