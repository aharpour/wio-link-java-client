package nl.openweb.iot.dashboard.service;

import nl.openweb.iot.dashboard.domain.Task;

interface HandlerFactory<T> {
    T build(Task task);
}
