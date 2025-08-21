import java.util.Scanner;

public class Duke {
    private static final int LINE_BREAK_LENGTH = 45;

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printLineBreak();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        printLineBreak();
        while(true) {
            String input = scanner.next();
            switch (input) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    printLineBreak();
                    return;
                default:
                    System.out.println(input);
                    printLineBreak();
            }
        }

    }
}
