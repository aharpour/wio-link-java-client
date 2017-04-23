package nl.openweb.iot.dashboard.service.script;

import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import nl.openweb.iot.dashboard.service.NotificationService;
import nl.openweb.iot.wio.NodeDecorator;
import nl.openweb.iot.wio.WioException;

public class SandboxFilter {

    private static final Set<String> ALLOWED_PACKAGES;
    private static final Set<String> ALLOWED_BASE_PACKAGES;
    private static final Set<Class<?>> ALLOWED_CLASSES;

    static {

        Set<String> basePackages = new HashSet<>();
        basePackages.add("groovy.scripts");
        basePackages.add("nl.openweb.iot.wio.domain");

        HashSet<String> packages = new HashSet<>();
        packages.add("java.lang");
        packages.add("java.util");
        packages.add("java.text");
        packages.add("java.time");

        packages.add("org.slf4j");
        packages.add("org.codehaus.groovy.runtime");
        packages.add("groovy.runtime.metaclass.groovy.scripts");
        packages.add("ch.qos.logback.classic");
        packages.add("nl.openweb.iot.monitor.domain");
        packages.add("nl.openweb.iot.wio.scheduling");
        packages.add("nl.openweb.iot.dashboard.service.script");

        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(WioException.class);
        classes.add(AbstractGroovyTaskHandler.class);
        classes.add(AbstractGroovyEventHandler.class);
        classes.add(PrintStream.class);
        classes.add(NodeDecorator.class);
        classes.add(NotificationService.class);
        ALLOWED_PACKAGES = Collections.unmodifiableSet(packages);
        ALLOWED_BASE_PACKAGES = Collections.unmodifiableSet(basePackages);
        ALLOWED_CLASSES = Collections.unmodifiableSet(classes);
    }

    private SandboxFilter() {
    }

    public static boolean filter(String className) {
        boolean result = !allowedByPackage(className) && !allowedByPackage(getPackage(className));
        try {
            Class<?> aClass = Class.forName(className);
            result = result && filter(aClass);
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return result;
    }

    public static boolean filter(Class<?> aClass) {
        boolean result = true;
        if (Proxy.isProxyClass(aClass) || (aClass.getPackage() != null && allowedByPackage(aClass.getPackage().getName()))
            || isAllowedClass(aClass)) {
            result = false;
        }
        return result;
    }

    private static boolean isAllowedClass(Class<?> aClass) {
        return ALLOWED_CLASSES.stream().anyMatch(a -> a.isAssignableFrom(aClass));
    }

    private static String getPackage(String className) {
        String result = className;
        int lastIndexOf = className.lastIndexOf('.');
        if (lastIndexOf > 0) {
            result = className.substring(0, lastIndexOf);
        }
        return result;
    }

    private static boolean allowedByPackage(String name) {
        boolean result = false;
        if (StringUtils.isNotBlank(name)) {
            for (String p : ALLOWED_BASE_PACKAGES) {
                if (name.startsWith(p)) {
                    result = true;
                    break;
                }
            }

            if (!result) {
                result = ALLOWED_PACKAGES.contains(name);
            }
        }
        return result;
    }
}
