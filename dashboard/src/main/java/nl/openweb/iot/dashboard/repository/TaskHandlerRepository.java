package nl.openweb.iot.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.openweb.iot.dashboard.domain.TaskHandler;

/**
 * Spring Data JPA repository for the TaskHandler entity.
 */
@SuppressWarnings("unused")
public interface TaskHandlerRepository extends JpaRepository<TaskHandler,Long> {

}
