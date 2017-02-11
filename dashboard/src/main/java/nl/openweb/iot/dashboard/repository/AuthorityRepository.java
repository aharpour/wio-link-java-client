package nl.openweb.iot.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.openweb.iot.dashboard.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
