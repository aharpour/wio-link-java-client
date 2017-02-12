package nl.openweb.iot.dashboard.web.rest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.jhipster.web.util.ResponseUtil;
import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.domain.TaskHandler;
import nl.openweb.iot.dashboard.repository.TaskRepository;
import nl.openweb.iot.dashboard.service.EventHandlerFactory;
import nl.openweb.iot.dashboard.service.TaskHandlerFactory;
import nl.openweb.iot.dashboard.web.rest.util.HeaderUtil;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.SchedulingService;
import nl.openweb.iot.wio.scheduling.SchedulingUtils;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

/**
 * REST controller for managing Task.
 */
@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "task";

    private final TaskRepository taskRepository;
    private final SchedulingService schedulingService;
    private final ApplicationContext context;

    public TaskResource(TaskRepository taskRepository, SchedulingService schedulingService, ApplicationContext context) {
        this.taskRepository = taskRepository;
        this.schedulingService = schedulingService;
        this.context = context;
    }

    /**
     * POST  /tasks : Create a new task.
     *
     * @param task the task to create
     * @return the ResponseEntity with status 201 (Created) and with body the new task, or with status 400 (Bad Request) if the task has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tasks")
    @Timed
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to save Task : {}", task);
        if (task.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new task cannot already have an ID")).body(null);
        }

        if (task.getNode() == null || StringUtils.isBlank(task.getNode().getNodeSn())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "nodeisrequired", "Node is required")).body(null);
        }
        try {
            ScheduledTask taskHandler = getTaskHandler(task);
            SchedulingService.TaskBuilder builder =
                schedulingService.build(taskHandler, task.getNode().getNodeSn())
                    .setKeepAwake(task.isKeepAwake())
                    .setForceSleep(task.isForceSleep());
            if (task.getEventHandler() != null) {
                builder.setEventHandler(getEventHandler(task));
            }
            task.setId(builder.build());
            Task result = taskRepository.save(task);
            return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
                .body(result);
        } catch (WioException e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "wioexception", e.getMessage())).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "exception", e.getMessage())).body(null);
        }

    }

    private ScheduledTask getTaskHandler(Task task) throws ClassNotFoundException {
        ScheduledTask result = (n, c) -> SchedulingUtils.hoursLater(5);
        TaskHandler taskHandler = task.getTaskHandler();
        if (taskHandler != null && StringUtils.isNotBlank(taskHandler.getClassName())) {
            String factoryName = taskHandler.getClassName();
            Class<?> factoryClass = Class.forName(factoryName);
            if (TaskHandlerFactory.class.isAssignableFrom(factoryClass)) {
                TaskHandlerFactory factory = (TaskHandlerFactory) context.getBean(factoryClass);
                result = factory.build(task);
            } else {
                log.error("the give class " + factoryClass.getName() + " is not a instance of " + TaskHandlerFactory.class.getName());
            }
        }
        return result;
    }

    private TaskEventHandler getEventHandler(Task task) throws ClassNotFoundException {
        TaskEventHandler result = (e, n, c) -> {
        };
        TaskHandler eventHandler = task.getTaskHandler();
        if (eventHandler != null && StringUtils.isNotBlank(eventHandler.getClassName())) {
            String factoryName = eventHandler.getClassName();
            Class<?> factoryClass = Class.forName(factoryName);
            if (EventHandlerFactory.class.isAssignableFrom(factoryClass)) {
                EventHandlerFactory factory = (EventHandlerFactory) context.getBean(factoryClass);
                result = factory.build(task);
            } else {
                log.error("the give class " + factoryClass.getName() + " doesn't extends " + EventHandlerFactory.class.getName());
            }
        }
        return result;
    }

    /**
     * GET  /tasks : get all the tasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tasks in body
     */
    @GetMapping("/tasks")
    @Timed
    public List<Task> getAllTasks() {
        log.debug("REST request to get all Tasks");
        return taskRepository.findAll();
    }

    /**
     * GET  /tasks/:id : get the "id" task.
     *
     * @param id the id of the task to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the task, or with status 404 (Not Found)
     */
    @GetMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        log.debug("REST request to get Task : {}", id);
        Task task = taskRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(task));
    }

    /**
     * DELETE  /tasks/:id : delete the "id" task.
     *
     * @param id the id of the task to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        log.debug("REST request to delete Task : {}", id);
        schedulingService.terminateTask(id);
        taskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
