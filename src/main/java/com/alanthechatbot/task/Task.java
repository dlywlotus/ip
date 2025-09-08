package com.alanthechatbot.task;

import com.alanthechatbot.exceptions.EmptyDescriptionException;

public class Task {
    protected String description;
    protected boolean isDone;
    protected String tag;

    public Task(String description) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("com.alanthechatbot.task.Task description is required!");
        }
        this.description = description;
        this.isDone = false;
        this.tag = "";
    }

    /**
     * Retrieves the completed status icon of the task
     *
     * @return the completed status of an icon
     */
    public boolean descriptionContains(String string) {
        return description.contains(string);
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[  ]"); // mark done task with X
    }

    public String getTagString() {
        return tag.isEmpty() ? "" : "#" + tag;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description + " " + getTagString();
    }

}
