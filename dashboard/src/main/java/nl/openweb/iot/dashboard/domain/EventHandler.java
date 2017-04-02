package nl.openweb.iot.dashboard.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import nl.openweb.iot.dashboard.domain.enumeration.Langauge;

/**
 * A EventHandler.
 */
@Entity
@Table(name = "event_handler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventHandler implements Serializable, HandlerBean {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "langauge", nullable = false)
    private Langauge langauge;

    @NotNull
    @Lob
    @Column(name = "code", nullable = false)
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EventHandler name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Langauge getLangauge() {
        return langauge;
    }

    public EventHandler langauge(Langauge langauge) {
        this.langauge = langauge;
        return this;
    }

    public void setLangauge(Langauge langauge) {
        this.langauge = langauge;
    }

    public String getCode() {
        return code;
    }

    public EventHandler code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventHandler eventHandler = (EventHandler) o;
        if (eventHandler.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eventHandler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventHandler{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", langauge='" + langauge + "'" +
            ", code='" + code + "'" +
            '}';
    }
}
