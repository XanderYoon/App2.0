package com.example.app20.ui.todo;

public class TodoModel {
    private int id;
    private String task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public TodoModel(int id, String task) {
        this.id = id;
        this.task = task;
    }
}
