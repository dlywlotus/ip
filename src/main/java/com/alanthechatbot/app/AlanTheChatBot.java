package com.alanthechatbot.app;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import com.alanthechatbot.exceptions.EmptyDescriptionException;
import com.alanthechatbot.exceptions.InputParsingException;
import com.alanthechatbot.exceptions.InvalidActionTypeException;
import com.alanthechatbot.exceptions.StorageException;
import com.alanthechatbot.parse.ParsedInput;
import com.alanthechatbot.parse.Parser;
import com.alanthechatbot.storage.Storage;
import com.alanthechatbot.task.Deadline;
import com.alanthechatbot.task.Event;
import com.alanthechatbot.task.TaskList;
import com.alanthechatbot.task.Todo;
import com.alanthechatbot.utils.PrintUtils;

/**
 * The main class of the application
 */
public class AlanTheChatBot {
    private final TaskList  taskList = new TaskList();

    /**
     * Runs the parsed action
     * @param parsed the result of parsing an input
     * @return the status message
     */
    public String runParsedInput(ParsedInput parsed) {
        try {
            String actionType = parsed.getActionType();
            String message = "";

            switch (actionType) {
            case "todo":
                message = taskList.addTask(new Todo(parsed.getTaskDesc()));
                break;
            case "deadline":
                message = taskList.addTask(new Deadline(parsed.getTaskDesc(),
                        parsed.getDoneBy()));
                break;
            case "event":
                message = taskList.addTask(new Event(parsed.getTaskDesc(),
                        parsed.getFrom(), parsed.getTo()));
                break;
            case "mark":
                message = taskList.markTask(parsed.getIndex());
                break;
            case "delete":
                message = taskList.deleteTask(parsed.getIndex());
                break;
            case "find":
                message = taskList.findAll(parsed.getTaskDesc());
                break;
            case "list":
                message = taskList.printTasks();
                break;
            case "bye":
                message = "Bye. Hope to see you again soon!";
                PauseTransition delay = new PauseTransition(Duration.seconds(.75));
                delay.setOnFinished(event -> Platform.exit()); // exit after delay
                delay.play();
                break;
            case "invalid input":
                return "Invalid input. Please try again!";
            }
            return message;
        } catch (DateTimeParseException e) {
            return "Please follow the format YYYY-MM-DD for dates!";
        } catch (EmptyDescriptionException e) {
            return "Task description cannot be empty!";
        }
    }

    /**
     * Replay the actions stored in storage
     */
    public void replayStoredActions() throws StorageException {
        try {
            Scanner scanner = new Scanner(Storage.getFile());
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                Parser parser = new Parser(input);
                runParsedInput(parser.parse());
            }
        } catch (FileNotFoundException e) {
            throw new StorageException("Storage has not been initialized!");
        } catch (InputParsingException e) {
            throw new StorageException("Error parsing storage!");
        }
    }
}
