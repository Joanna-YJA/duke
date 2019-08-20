public class Event extends Task {
    protected String time;

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    public String toString() {
        return "[E][" + getStatusIcon() + "] " + getDescription() + " (at: " + this.time + ")\n";
    }
}
