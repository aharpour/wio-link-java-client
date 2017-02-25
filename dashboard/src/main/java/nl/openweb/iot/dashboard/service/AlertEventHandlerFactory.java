package nl.openweb.iot.dashboard.service;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import nl.openweb.iot.dashboard.domain.Authority;
import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.domain.User;
import nl.openweb.iot.dashboard.repository.UserRepository;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class AlertEventHandlerFactory implements EventHandlerFactory {
    @Override
    public TaskEventHandler build(Task task) {
        return (e, n, c) -> {
            MailService bean = c.getBean(MailService.class);
            UserRepository userRepository = c.getBean(UserRepository.class);
            String to = userRepository.findAllByActivatedIsTrue()
                .filter(u -> !u.getLogin().equals("system") &&
                    StringUtils.isNotBlank(u.getEmail()) &&
                    u.getAuthorities().stream().map(Authority::getName).anyMatch("ROLE_ADMIN"::equals))
                .map(User::getEmail).collect(Collectors.joining(", "));
            bean.sendEmail(to, "An event has been triggered.", e.toString(), false, false);
        };
    }
}
