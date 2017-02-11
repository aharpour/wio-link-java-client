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
import nl.openweb.iot.dashboard.web.rest.util.HeaderUtil;
import nl.openweb.iot.data.JpaNodeBean;
import nl.openweb.iot.data.JpaNodeRepository;
import nl.openweb.iot.wio.NodeService;
import nl.openweb.iot.wio.WioException;

/**
 * REST controller for managing JpaNodeBean.
 */
@RestController
@RequestMapping("/api")
public class NodeResource {

    private final Logger log = LoggerFactory.getLogger(NodeResource.class);

    private static final String ENTITY_NAME = "node";

    private final JpaNodeRepository nodeRepository;

    private final NodeService nodeService;

    public NodeResource(JpaNodeRepository nodeRepository, NodeService nodeService) {
        this.nodeRepository = nodeRepository;
        this.nodeService = nodeService;
    }

    /**
     * POST  /nodes : Create a new node.
     *
     * @param node the node to create
     * @return the ResponseEntity with status 201 (Created) and with body the new node, or with status 400 (Bad Request) if the node has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nodes")
    @Timed
    public ResponseEntity<JpaNodeBean> createNode(@Valid @RequestBody JpaNodeBean node) throws URISyntaxException {
        log.debug("REST request to save JpaNodeBean : {}", node);
        JpaNodeBean result = nodeRepository.save(node);
        try {
            this.nodeService.reinitializeNode(node.getNodeSn());
        } catch (Exception e) {
            log.debug("Reinitialization of the node failed.", e);
        }
        return ResponseEntity.created(new URI("/api/nodes/" + result.getNodeSn()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getNodeSn().toString()))
            .body(result);
    }
    @PutMapping("/nodes/reinitialize/{id}")
    @Timed
    public ResponseEntity<JpaNodeBean> reinitializeNode(@PathVariable String id) {
        ResponseEntity<JpaNodeBean> result;
        try {
            nodeService.reinitializeNode(id);
            JpaNodeBean node = nodeRepository.findOne(id);
            result = ((ResponseEntity.BodyBuilder) ResponseEntity.ok()).body(node);
        } catch (WioException e) {
            result = ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "initfailed", "Re-initialization of node failed.")).body(null);
        }
        return result;

    }

    /**
     * PUT  /nodes : Updates an existing node.
     *
     * @param node the node to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated node,
     * or with status 400 (Bad Request) if the node is not valid,
     * or with status 500 (Internal Server Error) if the node couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nodes")
    @Timed
    public ResponseEntity<JpaNodeBean> updateNode(@Valid @RequestBody JpaNodeBean node) throws URISyntaxException {
        log.debug("REST request to update JpaNodeBean : {}", node);
        if (node.getNodeSn() == null) {
            return createNode(node);
        }
        JpaNodeBean result = nodeRepository.save(node);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, node.getNodeSn().toString()))
            .body(result);
    }

    /**
     * GET  /nodes : get all the nodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodes in body
     */
    @GetMapping("/nodes")
    @Timed
    public List<JpaNodeBean> getAllNodes() {
        log.debug("REST request to get all Nodes");
        List<JpaNodeBean> nodes = nodeRepository.findAll();
        return nodes;
    }

    /**
     * GET  /nodes/:id : get the "id" node.
     *
     * @param id the id of the node to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the node, or with status 404 (Not Found)
     */
    @GetMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<JpaNodeBean> getNode(@PathVariable String id) {
        log.debug("REST request to get JpaNodeBean : {}", id);
        JpaNodeBean node = nodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(node));
    }

    /**
     * DELETE  /nodes/:id : delete the "id" node.
     *
     * @param id the id of the node to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNode(@PathVariable String id) {
        log.debug("REST request to delete JpaNodeBean : {}", id);
        nodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
