package com.alanthechatbot.app;

import com.alanthechatbot.exceptions.EmptyDescriptionException;
import com.alanthechatbot.exceptions.InputParsingException;
import com.alanthechatbot.exceptions.InvalidActionTypeException;
import com.alanthechatbot.parse.ParsedInput;
import com.alanthechatbot.parse.Parser;
import com.alanthechatbot.task.Deadline;
import com.alanthechatbot.task.Event;
import com.alanthechatbot.task.TaskList;
import com.alanthechatbot.task.Todo;
import com.alanthechatbot.utils.PrintUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AlanTheChatBot {
    public static TaskList taskList = new TaskList();

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
                    case "list":
                        taskList.printTasks();
                        break;
                    case "bye":
                        System.out.println("Bye. Hope to see you again soon!");
                        return;
                    case "invalid input":
                        throw new InvalidActionTypeException("Invalid input. Please try again.");
                }
                String home = System.getProperty("user.home");
                Path filePath = Paths.get(home, "AlanTheChatBot", "data.txt");
                if (!Files.exists(filePath)) {
                    Files.createDirectories(filePath.getParent());
                    Files.createFile(filePath);
                } else {
                    if (canWrite && !parsed.getActionType().equals("list") &&
                            !parsed.getActionType().equals("bye")) {
                        writeToFile(filePath, input + "\n");
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please follow the format YYYY-MM-DD for dates!");
            } catch (EmptyDescriptionException | IOException
                     | InvalidActionTypeException | InputParsingException e) {
                System.out.println(e.getMessage());
            }
            if (canPrint) {
                PrintUtils.printLineBreak();
            }
        }
    }

    private static void writeToFile(Path filePath, String line) {
        try {
            FileWriter fw = new FileWriter(filePath.toString(), true);
            fw.write(line);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void retrieveTasks() {
        String home = System.getProperty("user.home");
        Path filePath = Paths.get(home, "AlanTheChatBot", "data.txt");
        try {
            if (!Files.exists(filePath)) {
                System.out.println("file does not exists - creating new file");
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } else {
                //read from file
                File logFile = new File(filePath.toString());
                Scanner fileScanner = new Scanner(logFile);
                processInputs(fileScanner, false, false);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintUtils.printIntro();
        retrieveTasks();
        processInputs(scanner, true, true);
    }
}
