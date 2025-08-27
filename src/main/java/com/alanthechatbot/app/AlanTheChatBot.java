package com.alanthechatbot.app;

import com.alanthechatbot.exceptions.EmptyDescriptionException;
import com.alanthechatbot.exceptions.InputParsingException;
import com.alanthechatbot.exceptions.InvalidActionTypeException;
import com.alanthechatbot.parse.ParsedInput;
import com.alanthechatbot.parse.Parser;
import com.alanthechatbot.storage.Storage;
import com.alanthechatbot.task.Deadline;
import com.alanthechatbot.task.Event;
import com.alanthechatbot.task.TaskList;
import com.alanthechatbot.task.Todo;
import com.alanthechatbot.utils.PrintUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The main class of the application
 */
public class AlanTheChatBot {
    public static TaskList taskList = new TaskList();

    /**
     * Translate text inputs into actions that are carried out.
     *<p>Reads the inputs via a text file or the command line.
     * Maps each line of input to a specific action
     * @param scanner the scanner to read a text file or user input via the command line
     * @param canWrite whether the actions should be written to storage
     * @param canPrint whether each event should be printed
     */
    private static void processInputs(Scanner scanner, boolean canWrite, boolean canPrint) {
        while (scanner.hasNextLine()) {
            try {
                String input = scanner.nextLine();
                Parser inputParser = new Parser(input);
                ParsedInput parsed = inputParser.parse();
                String actionType = parsed.getActionType();

                switch (actionType) {
                case "todo":
                    taskList.addTask(new Todo(parsed.getTaskDesc()), canPrint);
                    break;
                case "deadline":
                    taskList.addTask(new Deadline(parsed.getTaskDesc(),
                            parsed.getDoneBy()), canPrint);
                    break;
                case "event":
                    taskList.addTask(new Event(parsed.getTaskDesc(),
                            parsed.getFrom(), parsed.getTo()), canPrint);
                    break;
                case "mark":
                    taskList.markTask(parsed.getIndex(), canPrint);
                    break;
                case "delete":
                    taskList.deleteTask(parsed.getIndex(), canPrint);
                    break;
                case "find":
                    taskList.findAll(parsed.getTaskDesc());
                    break;
                case "list":
                    taskList.printTasks();
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    return;
                case "invalid input":
                    throw new InvalidActionTypeException("Invalid input. Please try again.");
                }
                if (canWrite && !parsed.getActionType().equals("list") &&
                        !parsed.getActionType().equals("bye") &&
                        !parsed.getActionType().equals("find")) {
                    Storage.writeToFile(input + "\n");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please follow the format YYYY-MM-DD for dates!");
            } catch (EmptyDescriptionException | InvalidActionTypeException
                     | InputParsingException e) {
                System.out.println(e.getMessage());
            }
            if (canPrint) {
                PrintUtils.printLineBreak();
            }
        }
    }

    /**
     * Processes the inputs stored in the storage file
     */
    private static void retrieveTasks() {
        //read from file
        try {
            Scanner fileScanner = new Scanner(Storage.getFile());
            processInputs(fileScanner, false, false);
        } catch (FileNotFoundException e) {
            System.out.println("Storage has not been initialized!");
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            PrintUtils.printIntro();
            Storage.init();
            retrieveTasks();
            processInputs(scanner, true, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
