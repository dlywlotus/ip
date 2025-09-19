package com.alanthechatbot.task;

import com.alanthechatbot.exceptions.EmptyDescriptionException;

/**
 * The default extension of the task class.
 */
public class Todo extends Task {
    public Todo(String description) throws EmptyDescriptionException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon()
                + " " + description + " " + getTagString();
    }
}
