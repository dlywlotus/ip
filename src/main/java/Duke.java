import java.security.InvalidParameterException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    private static enum IDK{
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

    private static void addTask(Task task) {
        taskList.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        printListSizeString();
    }

    private static void markTask(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("  %s\n", task.toString());

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

    private static void deleteTask(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new IllegalArgumentException("The task id is invalid!");
        }
        Task task = taskList.remove(taskId - 1);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        printListSizeString();

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printIntro();
        while(true) {
            int i;

            String input = scanner.nextLine();
            ArrayList<String> inputWords = new ArrayList<>(Arrays.asList(input.split(" ")));
            String firstWord = inputWords.get(0);

            ArrayList<String> taskDesc = new ArrayList<>();
            ArrayList<String> doneBy = new ArrayList<>();
            ArrayList<String> from = new ArrayList<>();
            ArrayList<String> to = new ArrayList<>();

            switch (firstWord) {
                case "todo":
                    String desc = inputWords.size() >= 2 ? inputWords.get(1) : "";
                    try {
                        addTask(new Todo(desc));
                    } catch (EmptyDescriptionError e) {
                        System.out.println(e.getMessage());
                    }
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
                    try {
                        addTask(new Deadline(String.join(" ", taskDesc),
                                String.join(" ", doneBy)));
                    } catch (IllegalArgumentException | EmptyDescriptionError e) {
                        System.out.println(e.getMessage());
                    }

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
                    try {
                        addTask(new Event(String.join(" ", taskDesc),
                                String.join(" ", from),
                                String.join(" ", to)));
                    } catch (EmptyDescriptionError e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "mark":
                    try {
                        if (inputWords.size() < 2) {
                            throw new IllegalArgumentException("Please provide the task id!");
                        }
                        markTask(Integer.parseInt(inputWords.get(1)));

                    } catch (NumberFormatException e) {
                        System.out.println("The taskId should be a integer!");
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        if (inputWords.size() < 2) {
                            throw new IllegalArgumentException("Please provide the task id!");
                        }
                        deleteTask(Integer.parseInt(inputWords.get(1)));
                    } catch (NumberFormatException e) {
                        System.out.println("The taskId should be a integer!");
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list":
                    printTasks();
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");

                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
           printLineBreak();
        }

    }
}
