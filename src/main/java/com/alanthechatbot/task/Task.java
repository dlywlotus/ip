package com.alanthechatbot.task;

import com.alanthechatbot.exceptions.EmptyDescriptionException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("com.alanthechatbot.task.Task description is required!");
        }
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public String toString() {
        return getStatusIcon() + " " + description;
    }

}
