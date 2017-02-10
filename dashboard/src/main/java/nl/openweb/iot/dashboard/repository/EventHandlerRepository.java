package nl.openweb.iot.dashboard.repository;

import nl.openweb.iot.dashboard.domain.EventHandler;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventHandler entity.
 */
@SuppressWarnings("unused")
public interface EventHandlerRepository extends JpaRepository<EventHandler,Long> {

}
