public class Todo extends Task {
    public Todo(String description) throws EmptyDescriptionError {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.getStatusIcon() + " " + description;
    }
}
