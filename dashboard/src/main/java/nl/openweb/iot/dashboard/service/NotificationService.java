package nl.openweb.iot.dashboard.service;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.openweb.iot.dashboard.domain.Authority;
import nl.openweb.iot.dashboard.domain.User;
import nl.openweb.iot.dashboard.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class NotificationService {

    private static final String EMAIL_PATTERN = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    private MailService mailService;
    private UserRepository userRepository;

    public NotificationService(MailService mailService, UserRepository userRepository) {
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    public void notifyAllUsers(String subject, String body, Type type) {
        notifyUsers(u -> true, subject, body, type);
    }


    public void notifyUsersOfRole(String role, String subject, String body, Type type) {
        Predicate<User> userFilter = u -> u.getAuthorities().stream()
            .map(Authority::getName).anyMatch(a -> a.equals(role));
        notifyUsers(userFilter, subject, body, type);
    }

    public void notifyUsers(Predicate<User> filterUsers, String subject, String body, Type type) {
        switch (type) {
            case EMAIL:
                sendEmailToUsers(filterUsers, subject, body);
                break;
            case PUSH_NOTIFICATION:
                throw new UnsupportedOperationException("Push notification is not supposed yet.");
        }
    }

    private void sendEmailToUsers(Predicate<User> filterUsers, String subject, String body) {
        String to = userRepository.findAllByActivatedIsTrue()
            .filter(filterUsers)
            .map(User::getEmail)
            .filter(Objects::nonNull)
            .filter(em -> Pattern.matches(EMAIL_PATTERN, em))
            .collect(Collectors.joining(", "));
        if (StringUtils.isNotBlank(to)) {
            mailService.sendEmail(to, subject, body, false, false);
        }
    }


    public enum Type {
        EMAIL, PUSH_NOTIFICATION
    }
}
