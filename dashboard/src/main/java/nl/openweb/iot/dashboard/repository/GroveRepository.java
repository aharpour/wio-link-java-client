package nl.openweb.iot.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.openweb.iot.data.JpaGroveBean;


/**
 * Spring Data JPA repository for the JpaGroveBean entity.
 */
@SuppressWarnings("unused")
public interface GroveRepository extends JpaRepository<JpaGroveBean,Long> {

}
