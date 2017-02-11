package nl.openweb.iot.dashboard.web.rest;

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
