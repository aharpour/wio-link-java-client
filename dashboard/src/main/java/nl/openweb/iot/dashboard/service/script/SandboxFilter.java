package nl.openweb.iot.dashboard.service.script;

import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.openweb.iot.wio.NodeDecorator;
import nl.openweb.iot.wio.WioException;

public class SandboxFilter {

    private static final Logger LOG = LoggerFactory.getLogger(SandboxFilter.class);

    private static final Set<String> ALLOWED_PACKAGES;
    private static final Set<Class<?>> ALLOWED_CLASSES;

    static {
        HashSet<String> packages = new HashSet<>();
        packages.add("java.lang");
        packages.add("java.util");
        packages.add("java.text");
        packages.add("java.time");
        packages.add("groovy.scripts");
        packages.add("org.slf4j");
        packages.add("org.codehaus.groovy.runtime");
        packages.add("groovy.runtime.metaclass.groovy.scripts");
        packages.add("ch.qos.logback.classic");
        packages.add("nl.openweb.iot.wio.domain");
        packages.add("nl.openweb.iot.wio.domain.grove");
        packages.add("nl.openweb.iot.monitor.domain");
        packages.add("nl.openweb.iot.wio.scheduling");
        packages.add("nl.openweb.iot.dashboard.service.script");

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

    public static boolean   filter(String className) {
        boolean result = !ALLOWED_PACKAGES.contains(className) && !ALLOWED_PACKAGES.contains(getPackage(className));
        try {
            Class<?> aClass = Class.forName(className);
            result = result && filter(aClass);
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return result;
    }

    private static String getPackage(String className) {
        String result = className;
        int lastIndexOf = className.lastIndexOf('.');
        if (lastIndexOf > 0) {
            result = className.substring(0, lastIndexOf);
        }
        return result;
    }

    public static boolean filter(Class<?> aClass) {
        boolean result = true;
        if (Proxy.isProxyClass(aClass) || ALLOWED_PACKAGES.contains(aClass.getPackage().getName()) || ALLOWED_CLASSES.contains(aClass)) {
            result = false;
        }
        return result;
    }
}
