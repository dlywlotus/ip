package com.alanthechatbot.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.alanthechatbot.exceptions.EmptyDescriptionException;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, String from, String to) throws EmptyDescriptionException, DateTimeParseException {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.getStatusIcon() + " " + this.description + " (from: "
                + from.format(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.ENGLISH))
                + " to: "
                + to.format(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.ENGLISH))
                + ") " + getTagString();
    }
}
