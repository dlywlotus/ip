public class Deadline extends Task {
    protected String doneBy;

    public Deadline(String description, String doneBy) {
        super(description);
        this.doneBy = doneBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + description + " (by: " + doneBy + ")";
    }
}
