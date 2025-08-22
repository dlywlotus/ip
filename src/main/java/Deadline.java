public class Deadline extends Task{
    protected String doneBy;

    public Deadline(String description, String doneBy) throws EmptyDescriptionError{
        super(description);
        this.doneBy = doneBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + description + " (by: " + doneBy + ")";
    }
}
