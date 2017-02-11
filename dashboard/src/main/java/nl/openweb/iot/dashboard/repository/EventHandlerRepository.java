package nl.openweb.iot.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.openweb.iot.dashboard.domain.EventHandler;

/**
 * Spring Data JPA repository for the EventHandler entity.
 */
@SuppressWarnings("unused")
public interface EventHandlerRepository extends JpaRepository<EventHandler,Long> {

}
