package nl.openweb.iot.dashboard.service;

import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@FunctionalInterface
interface EventHandlerFactory extends HandlerFactory<TaskEventHandler>{
}
