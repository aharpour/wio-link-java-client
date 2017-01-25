package nl.openweb.iot.wio.scheduling;

import java.util.Calendar;

public class SchedulingUtils {

    private SchedulingUtils() {
        // to prevent instantiation
    }

    public static ScheduledTask.TaskExecutionResult secondsLater(int seconds) {
        return new ScheduledTask.TaskExecutionResult(later(Calendar.SECOND, seconds));
    }

    public static ScheduledTask.TaskExecutionResult minutesLater(int minutes) {
        return new ScheduledTask.TaskExecutionResult(later(Calendar.MINUTE, minutes));
    }

    public static ScheduledTask.TaskExecutionResult hoursLater(int hours) {
        return new ScheduledTask.TaskExecutionResult(later(Calendar.HOUR, hours));
    }

    public static Calendar later(int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, amount);
        return calendar;
    }
}
