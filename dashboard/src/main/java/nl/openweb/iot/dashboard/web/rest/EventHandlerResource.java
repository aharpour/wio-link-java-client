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
import nl.openweb.iot.dashboard.domain.EventHandler;
import nl.openweb.iot.dashboard.repository.EventHandlerRepository;
import nl.openweb.iot.dashboard.web.rest.util.HeaderUtil;

/**
 * REST controller for managing EventHandler.
 */
@RestController
@RequestMapping("/api")
public class EventHandlerResource {

    private final Logger log = LoggerFactory.getLogger(EventHandlerResource.class);

    private static final String ENTITY_NAME = "eventHandler";

    private final EventHandlerRepository eventHandlerRepository;

    public EventHandlerResource(EventHandlerRepository eventHandlerRepository) {
        this.eventHandlerRepository = eventHandlerRepository;
    }

    /**
     * POST  /event-handlers : Create a new eventHandler.
     *
     * @param eventHandler the eventHandler to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventHandler, or with status 400 (Bad Request) if the eventHandler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-handlers")
    @Timed
    public ResponseEntity<EventHandler> createEventHandler(@Valid @RequestBody EventHandler eventHandler) throws URISyntaxException {
        log.debug("REST request to save EventHandler : {}", eventHandler);
        if (eventHandler.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eventHandler cannot already have an ID")).body(null);
        }
        EventHandler result = eventHandlerRepository.save(eventHandler);
        return ResponseEntity.created(new URI("/api/event-handlers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-handlers : Updates an existing eventHandler.
     *
     * @param eventHandler the eventHandler to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventHandler,
     * or with status 400 (Bad Request) if the eventHandler is not valid,
     * or with status 500 (Internal Server Error) if the eventHandler couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-handlers")
    @Timed
    public ResponseEntity<EventHandler> updateEventHandler(@Valid @RequestBody EventHandler eventHandler) throws URISyntaxException {
        log.debug("REST request to update EventHandler : {}", eventHandler);
        if (eventHandler.getId() == null) {
            return createEventHandler(eventHandler);
        }
        EventHandler result = eventHandlerRepository.save(eventHandler);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventHandler.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-handlers : get all the eventHandlers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eventHandlers in body
     */
    @GetMapping("/event-handlers")
    @Timed
    public List<EventHandler> getAllEventHandlers() {
        log.debug("REST request to get all EventHandlers");
        List<EventHandler> eventHandlers = eventHandlerRepository.findAll();
        return eventHandlers;
    }

    /**
     * GET  /event-handlers/:id : get the "id" eventHandler.
     *
     * @param id the id of the eventHandler to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventHandler, or with status 404 (Not Found)
     */
    @GetMapping("/event-handlers/{id}")
    @Timed
    public ResponseEntity<EventHandler> getEventHandler(@PathVariable Long id) {
        log.debug("REST request to get EventHandler : {}", id);
        EventHandler eventHandler = eventHandlerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eventHandler));
    }

    /**
     * DELETE  /event-handlers/:id : delete the "id" eventHandler.
     *
     * @param id the id of the eventHandler to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-handlers/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventHandler(@PathVariable Long id) {
        log.debug("REST request to delete EventHandler : {}", id);
        eventHandlerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
