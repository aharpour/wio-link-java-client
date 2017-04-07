package nl.openweb.iot.dashboard.web.rest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.jhipster.web.util.ResponseUtil;
import nl.openweb.iot.dashboard.domain.TaskHandler;
import nl.openweb.iot.dashboard.repository.TaskHandlerRepository;
import nl.openweb.iot.dashboard.service.dto.TaskHandlerDTO;
import nl.openweb.iot.dashboard.web.rest.util.HeaderUtil;

/**
 * REST controller for managing TaskHandler.
 */
@RestController
@RequestMapping("/api")
public class TaskHandlerResource {

    private final Logger log = LoggerFactory.getLogger(TaskHandlerResource.class);

    private static final String ENTITY_NAME = "taskHandler";

    private final TaskHandlerRepository taskHandlerRepository;

    public TaskHandlerResource(TaskHandlerRepository taskHandlerRepository) {
        this.taskHandlerRepository = taskHandlerRepository;
    }

    /**
     * POST  /task-handlers : Create a new taskHandler.
     *
     * @param taskHandler the taskHandler to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskHandler, or with status 400 (Bad Request) if the taskHandler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-handlers")
    @Timed
    public ResponseEntity<TaskHandlerDTO> createTaskHandler(@Valid @RequestBody TaskHandlerDTO taskHandler) throws URISyntaxException {
        log.debug("REST request to save TaskHandler : {}", taskHandler);
        if (taskHandler.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new taskHandler cannot already have an ID")).body(null);
        }
        TaskHandler result = taskHandlerRepository.save(taskHandler.toTaskHandler());
        return ResponseEntity.created(new URI("/api/task-handlers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(new TaskHandlerDTO(result));
    }

    /**
     * PUT  /task-handlers : Updates an existing taskHandler.
     *
     * @param taskHandler the taskHandler to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskHandler,
     * or with status 400 (Bad Request) if the taskHandler is not valid,
     * or with status 500 (Internal Server Error) if the taskHandler couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-handlers")
    @Timed
    public ResponseEntity<TaskHandlerDTO> updateTaskHandler(@Valid @RequestBody TaskHandlerDTO taskHandler) throws URISyntaxException {
        log.debug("REST request to update TaskHandler : {}", taskHandler);
        if (taskHandler.getId() == null) {
            return createTaskHandler(taskHandler);
        }
        TaskHandler result = taskHandlerRepository.save(taskHandler.toTaskHandler());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskHandler.getId().toString()))
            .body(new TaskHandlerDTO(result));
    }

    /**
     * GET  /task-handlers : get all the taskHandlers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of taskHandlers in body
     */
    @GetMapping("/task-handlers")
    @Timed
    public List<TaskHandler> getAllTaskHandlers() {
        log.debug("REST request to get all TaskHandlers");
        return taskHandlerRepository.findAll();
    }

    /**
     * GET  /task-handlers/:id : get the "id" taskHandler.
     *
     * @param id the id of the taskHandler to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskHandler, or with status 404 (Not Found)
     */
    @GetMapping("/task-handlers/{id}")
    @Timed
    public ResponseEntity<TaskHandlerDTO> getTaskHandler(@PathVariable Long id) {
        log.debug("REST request to get TaskHandler : {}", id);
        TaskHandler taskHandler = taskHandlerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskHandler).map(TaskHandlerDTO::new));
    }

    /**
     * DELETE  /task-handlers/:id : delete the "id" taskHandler.
     *
     * @param id the id of the taskHandler to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-handlers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskHandler(@PathVariable Long id) {
        log.debug("REST request to delete TaskHandler : {}", id);
        taskHandlerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
