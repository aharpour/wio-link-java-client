package nl.openweb.iot.dashboard.repository;

import nl.openweb.iot.dashboard.domain.Node;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Node entity.
 */
@SuppressWarnings("unused")
public interface NodeRepository extends JpaRepository<Node,Long> {

}
