import java.util.ArrayList;
import java.util.List;

public class SnapshotRecorder {
    private static final List<SnapshotEvent> events = new ArrayList<>();

    public static void record(SnapshotEvent event) {
        events.add(event);
    }

    public static List<SnapshotEvent> getEvents() {
        return events;
    }

    public static void clear() {
        events.clear();
    }
}
