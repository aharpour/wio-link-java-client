package nl.openweb.iot.dashboard.service.script;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.ClassFilter;

public class JsSandboxFilter implements ClassFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JsSandboxFilter.class);

    @Override
    public boolean exposeToScripts(String className) {
        return !SandboxFilter.filter(className);
    }
}
