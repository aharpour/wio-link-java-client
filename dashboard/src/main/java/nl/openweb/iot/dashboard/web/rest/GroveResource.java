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
import nl.openweb.iot.dashboard.repository.GroveRepository;
import nl.openweb.iot.dashboard.web.rest.util.HeaderUtil;
import nl.openweb.iot.data.JpaGroveBean;

/**
 * REST controller for managing JpaGroveBean.
 */
@RestController
@RequestMapping("/api")
public class GroveResource {

    private final Logger log = LoggerFactory.getLogger(GroveResource.class);

    private static final String ENTITY_NAME = "grove";

    private final GroveRepository groveRepository;

    public GroveResource(GroveRepository groveRepository) {
        this.groveRepository = groveRepository;
    }

    /**
     * POST  /groves : Create a new grove.
     *
     * @param grove the grove to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grove, or with status 400 (Bad Request) if the grove has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groves")
    @Timed
    public ResponseEntity<JpaGroveBean> createGrove(@Valid @RequestBody JpaGroveBean grove) throws URISyntaxException {
        log.debug("REST request to save JpaGroveBean : {}", grove);
        if (grove.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new grove cannot already have an ID")).body(null);
        }
        JpaGroveBean result = groveRepository.save(grove);
        return ResponseEntity.created(new URI("/api/groves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groves : Updates an existing grove.
     *
     * @param grove the grove to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grove,
     * or with status 400 (Bad Request) if the grove is not valid,
     * or with status 500 (Internal Server Error) if the grove couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/groves")
    @Timed
    public ResponseEntity<JpaGroveBean> updateGrove(@Valid @RequestBody JpaGroveBean grove) throws URISyntaxException {
        log.debug("REST request to update JpaGroveBean : {}", grove);
        if (grove.getId() == null) {
            return createGrove(grove);
        }
        JpaGroveBean result = groveRepository.save(grove);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grove.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groves : get all the groves.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groves in body
     */
    @GetMapping("/groves")
    @Timed
    public List<JpaGroveBean> getAllGroves() {
        log.debug("REST request to get all Groves");
        List<JpaGroveBean> groves = groveRepository.findAll();
        return groves;
    }

    /**
     * GET  /groves/:id : get the "id" grove.
     *
     * @param id the id of the grove to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grove, or with status 404 (Not Found)
     */
    @GetMapping("/groves/{id}")
    @Timed
    public ResponseEntity<JpaGroveBean> getGrove(@PathVariable Long id) {
        log.debug("REST request to get JpaGroveBean : {}", id);
        JpaGroveBean grove = groveRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grove));
    }

    /**
     * DELETE  /groves/:id : delete the "id" grove.
     *
     * @param id the id of the grove to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groves/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrove(@PathVariable Long id) {
        log.debug("REST request to delete JpaGroveBean : {}", id);
        groveRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
