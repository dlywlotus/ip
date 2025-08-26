import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    private static enum IDK {
        MIGHT, USE, THIS, IN, THE, FUTURE
    }

    private static final int LINE_BREAK_LENGTH = 45;
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    private static void printListSizeString() {
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
    }

    private static void addTask(Task task, boolean canPrint) {
        taskList.add(task);
        if (canPrint) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task.toString());
            printListSizeString();
        }
    }

    private static void deleteTask(int taskId, boolean canPrint) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.remove(taskId - 1);
        if (canPrint) {
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task.toString());
            printListSizeString();
        }
    }

    private static void markTask(int taskId, boolean canPrint) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        if (canPrint) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("  %s\n", task);
        }
    }

    private static void printTasks() {
        if (taskList.isEmpty()) {
            System.out.println("You have completed all your tasks! :)");
            return;
        }

        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("%d.%s\n", i + 1, task.toString());
        }

    }

    private static void printIntro() {
        printLineBreak();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        printLineBreak();
    }

    //Create a parser that parses each line and returns in the form:
    /*ParsedInput = {
        action:
        taskDesc:
        doneBy:
        from:
        to:
    }
    */
    private static void processInputs(Scanner scanner, boolean canWrite, boolean canPrint) {
        while (scanner.hasNextLine()) {
            int i;

            String input = scanner.nextLine();
            ArrayList<String> inputWords = new ArrayList<>(Arrays.asList(input.split(" ")));
            String firstWord = inputWords.get(0);

            ArrayList<String> taskDesc = new ArrayList<>();
            ArrayList<String> doneBy = new ArrayList<>();
            ArrayList<String> from = new ArrayList<>();
            ArrayList<String> to = new ArrayList<>();

            try {
                switch (firstWord) {
                    case "todo":
                        String desc = inputWords.size() >= 2 ? inputWords.get(1) : "";
                        addTask(new Todo(desc), canPrint);
                        break;
                    case "deadline":
                        i = 1;
                        while (i < inputWords.size() && !inputWords.get(i).equals("/by")) {
                            taskDesc.add(inputWords.get(i));
                            i += 1;
                        }
                        for (int j = i + 1; j < inputWords.size(); j++) {
                            doneBy.add(inputWords.get(j));
                        }
                        addTask(new Deadline(String.join(" ", taskDesc),
                                String.join(" ", doneBy)), canPrint);
                        break;
                    case "event":
                        taskDesc = new ArrayList<>();
                        i = 1;
                        while (i < inputWords.size() && !inputWords.get(i).equals("/from")) {
                            taskDesc.add(inputWords.get(i));
                            i += 1;
                        }
                        i += 1;
                        while (i < inputWords.size() && !inputWords.get(i).equals("/to")) {
                            from.add(inputWords.get(i));
                            i += 1;
                        }
                        for (int j = i + 1; j < inputWords.size(); j++) {
                            to.add(inputWords.get(j));
                        }
                        addTask(new Event(String.join(" ", taskDesc),
                                String.join(" ", from),
                                String.join(" ", to)), canPrint);
                        break;
                    case "mark":
                        if (inputWords.size() < 2) {
                            throw new IllegalArgumentException("Please provide the task id!");
                        }
                        markTask(Integer.parseInt(inputWords.get(1)), canPrint);
                        break;
                    case "delete":
                        if (inputWords.size() < 2) {
                            throw new IllegalArgumentException("Please provide the task id!");
                        }
                        deleteTask(Integer.parseInt(inputWords.get(1)), canPrint);
                        break;
                    case "list":
                        printTasks();
                        break;
                    case "bye":
                        System.out.println("Bye. Hope to see you again soon!");
                        return;
                    default:
                        throw new InvalidActionType("Invalid input. Please try again.");
                }
                String home = System.getProperty("user.home");
                Path filePath = Paths.get(home, "AlanTheChatBot", "data.txt");
                if (!Files.exists(filePath)) {
                    Files.createDirectories(filePath.getParent());
                    Files.createFile(filePath);
                } else {
                    if (canWrite && !inputWords.get(0).equals("bye")
                            && !inputWords.get(0).equals("list")) {
                        writeToFile(filePath, input + "\n");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("The task id should be an integer");
            } catch (DateTimeParseException e) {
                System.out.println("Please follow the format YYYY-MM-DD for dates!");
            } catch (EmptyDescriptionError | IllegalArgumentException
                     | IOException | InvalidActionType e) {
                System.out.println(e.getMessage());
            }
            if (canPrint) {
                printLineBreak();
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
        printIntro();
        retrieveTasks();
        processInputs(scanner, true, true);
    }
}
