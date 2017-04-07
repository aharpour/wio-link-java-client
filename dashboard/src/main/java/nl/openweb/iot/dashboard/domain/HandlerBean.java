package nl.openweb.iot.dashboard.domain;

import nl.openweb.iot.dashboard.domain.enumeration.Language;

public interface HandlerBean {

    String getCodeAsString();

    String getName();

    Language getLanguage();
}
