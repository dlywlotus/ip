import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final int LINE_BREAK_LENGTH = 45;
    private static ArrayList<String> taskList = new ArrayList<>();

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    private static void addTask(String task) {
        taskList.add(task);
        System.out.println("added: " + task);
        printLineBreak();
    }

    private static void printTasks() {
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, taskList.get(i));
        }
        System.out.println();
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
            switch (input) {
                case "list":
                    printTasks();
                    break;
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    printLineBreak();
                    return;
                default:
                    addTask(input);
            }
        }

    }
}
