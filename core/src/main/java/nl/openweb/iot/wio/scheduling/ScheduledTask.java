package nl.openweb.iot.wio.scheduling;

import java.util.Calendar;

import lombok.Getter;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.domain.Node;

@FunctionalInterface
public interface ScheduledTask {

    TaskExecutionResult execute(Node node, TaskContext context) throws WioException;

    @Getter
    public class TaskExecutionResult {
        private Calendar nextExecution;
        private boolean keepAwake = false;
        private boolean forceSleep = false;

        void validate() {
            if (nextExecution == null) {
                throw new IllegalArgumentException("nextExecution is required.");
            }
            if (keepAwake && forceSleep) {
                throw new IllegalArgumentException("keepAwake and forceSleep can not be true at the same time.");
            }
        }

        public TaskExecutionResult(Calendar nextExecution) {
            this.nextExecution = nextExecution;
        }

        public TaskExecutionResult(Calendar nextExecution, boolean keepAwake) {
            this.nextExecution = nextExecution;
            this.keepAwake = keepAwake;
        }

        public TaskExecutionResult(boolean forceSleep, Calendar nextExecution) {
            this.nextExecution = nextExecution;
            this.forceSleep = forceSleep;
        }

    }
}
