import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final int LINE_BREAK_LENGTH = 45;
    private static ArrayList<Task> taskList = new ArrayList<>();

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    private static void addTask(Task task) {
        taskList.add(task);
        System.out.println("added: " + task.description);
        printLineBreak();
    }

    private static void markTask(int taskId) {
        if (taskId > taskList.size() || taskId < 1) {
            throw new InvalidParameterException("The task does not exist");
        }
        Task task = taskList.get(taskId - 1);
        task.isDone = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.printf("  %s %s\n", task.getStatusIcon(), task.description);
        printLineBreak();
    }

    private static void printTasks() {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("%d.%s %s\n", i + 1, task.getStatusIcon(), task.description);
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
            String input = scanner.nextLine();
            String[] inputWords = input.split(" ");

            if (inputWords.length > 0 && inputWords[0].equals("mark")) {
                try {
                    if (inputWords.length < 2) {
                        throw new IllegalArgumentException("Missing task index");
                    }
                    String secondWord = inputWords[1];
                    markTask(Integer.parseInt(secondWord));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }

            switch (input) {
                case "list":
                    printTasks();
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    printLineBreak();
                    return;
                default:
                    addTask(new Task(input));
            }
        }

    }
}
