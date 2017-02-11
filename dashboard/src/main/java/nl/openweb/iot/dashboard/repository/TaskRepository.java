package nl.openweb.iot.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.openweb.iot.dashboard.domain.Task;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
public interface TaskRepository extends JpaRepository<Task,Long> {

}
