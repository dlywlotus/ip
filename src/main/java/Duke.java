public class Duke {
    private static final int LINE_BREAK_LENGTH = 80;

    private static void printLineBreak() {
        for (int i = 0; i < LINE_BREAK_LENGTH; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        printLineBreak();
        System.out.println("Hello! I'm Alan");
        System.out.println("What can I do for you?");
        printLineBreak();
        System.out.println("Bye. See you soon!");
        printLineBreak();
    }
}
