import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    protected LocalDate doneBy;

    public Deadline(String description, String doneBy) throws EmptyDescriptionError, DateTimeParseException {
        super(description);
        this.doneBy = LocalDate.parse(doneBy);
    }

    @Override
    public String toString() {
        return "[D]" + super.getStatusIcon() + " " + description + " (by: "
                + doneBy.format(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.ENGLISH))
                + ")";
    }
}
