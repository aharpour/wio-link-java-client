package nl.openweb.iot.dashboard.service;

import nl.openweb.iot.wio.scheduling.ScheduledTask;

@FunctionalInterface
interface TaskHandlerFactory extends HandlerFactory<ScheduledTask> {

}
