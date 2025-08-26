package com.alanthechatbot.task;

import com.alanthechatbot.exceptions.EmptyDescriptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskListTest {
    private static final TaskList dummyList = new TaskList();

    @Test
    public void deleteTask_invalidId_throwsException() {

        assertThrows(IllegalArgumentException.class,
                () -> dummyList.deleteTask(-1, false));
    }

    @Test
    public void addTask_validTask_sizeIncreases() throws EmptyDescriptionException {
        dummyList.addTask(new Todo("task1"), false);
        assertEquals(1, dummyList.size());
    }
}
