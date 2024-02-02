package com.example.app20.ui.assignment;

public class AssignmentModel {
    private int id;
    private String assignment, course, date;

    public AssignmentModel(int id, String assignment, String course, String date) {
        this.id = id;
        this.assignment = assignment;
        this.course = course;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
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
