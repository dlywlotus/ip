import java.security.InvalidParameterException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    private static final int LINE_BREAK_LENGTH = 45;
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    private static void addTask(Task task) {
        taskList.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        printLineBreak();
    }

    private static void markTask(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new InvalidParameterException("The task does not exist");
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("  %s\n", task.toString());
        printLineBreak();
    }

    private static void printTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("%d.%s\n", i + 1, task.toString());
        }
        printLineBreak();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printLineBreak();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        printLineBreak();
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
                    addTask(new Todo(inputWords.get(1)));
                    break;
                case "deadline":
                    i = 1;
                    while (!inputWords.get(i).equals("/by")) {
                        taskDesc.add(inputWords.get(i));
                        i += 1;
                    }
                    for (int j = i + 1; j < inputWords.size(); j++) {
                        doneBy.add(inputWords.get(j));
                    }
                    addTask(new Deadline(String.join(" ", taskDesc),
                            String.join(" ", doneBy)));
                    break;
                case "event":
                    taskDesc = new ArrayList<>();
                    i = 1;
                    while (!inputWords.get(i).equals("/from")) {
                        taskDesc.add(inputWords.get(i));
                        i += 1;
                    }
                    i += 1;
                    while (!inputWords.get(i).equals("/to")) {
                        from.add(inputWords.get(i));
                        i += 1;
                    }
                    for (int j = i + 1; j < inputWords.size(); j++) {
                        to.add(inputWords.get(j));
                    }
                    addTask(new Event(String.join(" ", taskDesc),
                            String.join(" ", from),
                            String.join(" ", to)));
                    break;
                case "mark":
                    markTask(Integer.parseInt(inputWords.get(1)));
                    break;
                case "list":
                    printTasks();
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    printLineBreak();
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }

    }
}
