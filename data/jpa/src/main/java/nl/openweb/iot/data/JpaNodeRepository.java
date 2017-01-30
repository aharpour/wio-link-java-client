package nl.openweb.iot.data;


import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNodeRepository extends JpaRepository<JpaNodeBean, String> {

    JpaNodeBean findOneByName(String name);

}
