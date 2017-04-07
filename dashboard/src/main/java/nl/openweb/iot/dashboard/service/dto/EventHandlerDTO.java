package nl.openweb.iot.dashboard.service.dto;


import javax.validation.constraints.NotNull;

import nl.openweb.iot.dashboard.domain.EventHandler;
import nl.openweb.iot.dashboard.domain.enumeration.Language;


public class EventHandlerDTO {

    public EventHandlerDTO() {
    }

    public EventHandlerDTO(EventHandler eventHandler) {
        this.id = eventHandler.getId();
        this.name = eventHandler.getName();
        this.language = eventHandler.getLanguage();
        this.code = eventHandler.getCodeAsString();
    }

    private Long id;
    @NotNull
    private String name;

    @NotNull
    private Language language;

    @NotNull
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

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EventHandler toEventHandler() {
        EventHandler eventHandler = new EventHandler();
        eventHandler.setId(id);
        eventHandler.setName(name);
        eventHandler.setLanguage(language);
        eventHandler.setCode(code.getBytes());
        return eventHandler;
    }
}
