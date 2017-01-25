package nl.openweb.iot.wio.scheduling;


import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.WioSettings;
import nl.openweb.iot.wio.domain.Node;
import nl.openweb.iot.wio.rest.WioRestException;

class TaskWrapper implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TaskWrapper.class);
    @Getter
    private final String taskId;
    private final Node node;
    private final SchedulingService schedulingService;
    private final TaskContext taskContext;
    private final ScheduledTask task;
    private final WioSettings settings;
    private boolean nodeCanBePutToSleep;
    @Setter
    private boolean keepAwake = false;
    @Setter
    private boolean forceSleep = false;
    @Setter
    private EventHandlerWrapper eventHandler;
    @Setter(AccessLevel.PACKAGE)
    private volatile boolean terminate = false;

    TaskWrapper(Node node, SchedulingService schedulingService, TaskContext context, ScheduledTask task, WioSettings settings) {
        this.taskId = UUID.randomUUID().toString();
        this.node = node;
        this.schedulingService = schedulingService;
        this.task = task;
        this.taskContext = context;
        this.settings = settings;
    }

    void init() {
        if (keepAwake && forceSleep) {
            throw new IllegalArgumentException("keepAwake and forceSleep can not be true at the same time.");
        }
        nodeCanBePutToSleep = (node.isPassive() && !keepAwake) || forceSleep;
        if (eventHandler != null) {
            node.setEventHandler(eventHandler);
        }
    }

    @Override
    public void run() {
        try {
            ScheduledTask.TaskExecutionResult result = task.execute(node, taskContext);
            result.validate();
            putToSleepIfNeeded(result, node);
            scheduleNextRun(result);
        } catch (WioRestException e) {
            if (e.getResponseStatus() == 408 || e.getResponseStatus() == 404) {
                logErrorAndRetry(e, settings.getRetryAfterTimeoutInSec());
            } else {
                logErrorAndRetry(e, settings.getRetryAfterErrorInSec());
            }
        } catch (Exception e) {
            logErrorAndRetry(e, settings.getRetryAfterErrorInSec());
        }
    }

    private void logErrorAndRetry(Exception e, int retryAfterErrorInSec) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, retryAfterErrorInSec);
        LOG.error(e.getMessage(), e);
        if (!terminate) {
            schedulingService.schedule(this, now.getTime());
        }
    }

    private void scheduleNextRun(ScheduledTask.TaskExecutionResult result) {
        if (!terminate) {
            Calendar now = Calendar.getInstance();
            if (result.getNextExecution().after(now)) {
                schedulingService.schedule(this, result.getNextExecution().getTime());
            } else {
                now.add(Calendar.SECOND, 2);
                schedulingService.schedule(this, now.getTime());
            }
        }
    }

    private void putToSleepIfNeeded(ScheduledTask.TaskExecutionResult result, Node node) throws WioException {
        if (canPutToSleep(result)) {
            Calendar sleepUntil = (Calendar) result.getNextExecution().clone();
            int warmUpPeriodInSeconds = Math.max(settings.getWarmUpPeriodInSeconds(), node.getWarmUpTime());
            sleepUntil.add(Calendar.SECOND, -1 * warmUpPeriodInSeconds);
            Calendar now = Calendar.getInstance();
            if (sleepUntil.after(now)) {
                long until = (sleepUntil.getTimeInMillis() - now.getTimeInMillis()) / 1000;
                this.node.sleep(new Long(until).intValue());
            }
        }
    }

    private boolean canPutToSleep(ScheduledTask.TaskExecutionResult executionResult) {
        return (nodeCanBePutToSleep && !executionResult.isKeepAwake()) || executionResult.isForceSleep();
    }


}
