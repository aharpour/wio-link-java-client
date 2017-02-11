package nl.openweb.iot.dashboard.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import nl.openweb.iot.dashboard.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return userName != null ? userName : Constants.SYSTEM_ACCOUNT;
    }
}
