import java.util.ArrayList;
import java.util.List;

public class AnimationRecorder {
    private static final List<AnimationEvent> events = new ArrayList<>();

    public static void recordEvent(AnimationEvent event) {
        events.add(event);
    }

    public static List<AnimationEvent> getEvents() {
        return events;
    }

    public static void clear() {
        events.clear();
    }
}
