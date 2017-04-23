package nl.openweb.iot.dashboard.service.script;

import org.kohsuke.groovy.sandbox.GroovyValueFilter;


public class GroovySandboxFilter extends GroovyValueFilter {

    @Override
    public Object filter(Object o) {
        if (o != null && SandboxFilter.filter(getClass(o))) {
            throw new SecurityException("Oops, type " + o.getClass() + " is not permitted to be used in an script");
        }
        return o;
    }

    private Class<?> getClass(Object o) {
        Class<?> classToCheck = o.getClass();
        if (classToCheck.isMemberClass()) {
            classToCheck = classToCheck.getDeclaringClass();
        }
        return classToCheck;
    }
}
