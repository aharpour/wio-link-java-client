package nl.openweb.iot.dashboard.service.script;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.openweb.iot.wio.NodeDecorator;
import nl.openweb.iot.wio.WioException;

public class SandboxFilter {
    private static final Set<String> ALLOWED_PACKAGES;
    private static final Set<Class<?>> ALLOWED_CLASSES;

    static {
        HashSet<String> packages = new HashSet<>();
        packages.add("java.lang");
        packages.add("java.util");
        packages.add("java.text");
        packages.add("java.time");
        packages.add("groovy.scripts");
        packages.add("nl.openweb.iot.wio.domain");
        packages.add("nl.openweb.iot.wio.domain.grove");
        packages.add("nl.openweb.iot.wio.scheduling");
        packages.add("ch.qos.logback.classic");
        packages.add("org.codehaus.groovy.runtime");

        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(WioException.class);
        classes.add(AbstractGroovyTaskHandler.class);
        classes.add(AbstractGroovyEventHandler.class);
        classes.add(PrintStream.class);
        classes.add(NodeDecorator.class);
        ALLOWED_PACKAGES = Collections.unmodifiableSet(packages);
        ALLOWED_CLASSES = Collections.unmodifiableSet(classes);
    }

    private SandboxFilter() {
    }


    public static boolean filter(Class<?> aClass) {
        boolean result = true;
        if (ALLOWED_PACKAGES.contains(aClass.getPackage().getName()) || ALLOWED_CLASSES.contains(aClass)) {
            result = false;
        }
        return result;
    }
}
