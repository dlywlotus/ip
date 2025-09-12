package com.alanthechatbot.task;

import com.alanthechatbot.exceptions.EmptyDescriptionException;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {
    @Test
    public void toString_validEvent_formatsCorrectly() throws EmptyDescriptionException {
        Event testEvent = new Event("fold clothes", "2025-12-10", "2025-12-12");
        assertEquals("[E][ ] fold clothes (from: 10 December 2025 to: 12 December 2025)", testEvent.toString());
    }

    @Test
    public void constructor_invalidDateFormat_throwsException() {
        assertThrows(DateTimeParseException.class,
                () -> new Event("fold clothes", "2025-12-1", "2025-12-2"));
    }

    @Test
    public void constructor_emptyDescription_throwsException() {
        assertThrows(EmptyDescriptionException.class,
                () ->  new Event("", "2025-12-01", "2025-12-02"));
    }

}
