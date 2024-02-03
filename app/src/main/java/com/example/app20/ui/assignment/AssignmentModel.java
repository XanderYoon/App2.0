package com.example.app20.ui.assignment;

import com.example.app20.ui.classes.ClassModel;

import java.util.Comparator;

public class AssignmentModel {
    private int id, month, day;
    private String assignment, course;

    public AssignmentModel(int id, String assignment, String course, int month, int day) {
        this.id = id;
        this.assignment = assignment;
        this.course = course;
        this.month = month;
        this.day = day;
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

    public static Comparator<AssignmentModel> courseSort = new Comparator<AssignmentModel>() {
        @Override
        public int compare(AssignmentModel o1, AssignmentModel o2) {
            return o1.getCourse().compareTo(o2.getCourse());
        }
    };

    public static Comparator<AssignmentModel> dateSort = new Comparator<AssignmentModel>() {
        @Override
        public int compare(AssignmentModel o1, AssignmentModel o2) {
            if (o1.getMonth() == o2.getMonth()) {
                return o1.getDay() - (o2.getDay());
            } else {
                return o1.getMonth() - o2.getMonth();
            }
        }
    };

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
