package nl.openweb.iot.dashboard.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import nl.openweb.iot.data.JpaNodeBean;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "period", nullable = false)
    private Double period;

    @Column(name = "force_sleep")
    private Boolean forceSleep;

    @Column(name = "keep_awake")
    private Boolean keepAwake;

    @OneToOne
    @JoinColumn(unique = true)
    private JpaNodeBean node;

    @OneToOne
    @JoinColumn(unique = true)
    private TaskHandler taskHandler;

    @OneToOne
    @JoinColumn(unique = true)
    private EventHandler eventHandler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPeriod() {
        return period;
    }

    public Task period(Double period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Double period) {
        this.period = period;
    }

    public Boolean isForceSleep() {
        return forceSleep;
    }

    public Task forceSleep(Boolean forceSleep) {
        this.forceSleep = forceSleep;
        return this;
    }

    public void setForceSleep(Boolean forceSleep) {
        this.forceSleep = forceSleep;
    }

    public Boolean isKeepAwake() {
        return keepAwake;
    }

    public Task keepAwake(Boolean keepAwake) {
        this.keepAwake = keepAwake;
        return this;
    }

    public void setKeepAwake(Boolean keepAwake) {
        this.keepAwake = keepAwake;
    }

    public JpaNodeBean getNode() {
        return node;
    }

    public Task node(JpaNodeBean node) {
        this.node = node;
        return this;
    }

    public void setNode(JpaNodeBean node) {
        this.node = node;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }

    public Task taskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
        return this;
    }

    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public Task eventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if (task.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", period='" + period + "'" +
            ", forceSleep='" + forceSleep + "'" +
            ", keepAwake='" + keepAwake + "'" +
            '}';
    }
}
