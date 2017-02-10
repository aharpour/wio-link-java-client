package nl.openweb.iot.dashboard.repository;

import nl.openweb.iot.dashboard.domain.TaskHandler;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaskHandler entity.
 */
@SuppressWarnings("unused")
public interface TaskHandlerRepository extends JpaRepository<TaskHandler,Long> {

}
