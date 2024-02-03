package com.example.app20.ui.exam;

public class ExamModel {
    private int id;
    private String exam, course, date;

    public ExamModel(int id, String exam, String course, String date) {
        this.id = id;
        this.exam = exam;
        this.course = course;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String assignment) {
        this.exam = assignment;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

