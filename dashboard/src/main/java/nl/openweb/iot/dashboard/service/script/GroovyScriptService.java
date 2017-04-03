package nl.openweb.iot.dashboard.service.script;

import javax.annotation.PostConstruct;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.kohsuke.groovy.sandbox.SandboxTransformer;
import org.springframework.stereotype.Service;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import nl.openweb.iot.dashboard.domain.HandlerBean;
import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.domain.enumeration.Language;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class GroovyScriptService {

    private CompilerConfiguration config;

    @PostConstruct
    public void init() {
        config = new CompilerConfiguration();
        config.addCompilationCustomizers(new SandboxTransformer());
    }

    public ScheduledTask createScheduledTask(Task taskBean) throws WioException {
        try {
            Class<?> scriptClass = getGroovyClass(taskBean.getTaskHandler());
            if (AbstractGroovyTaskHandler.class.isAssignableFrom(scriptClass)) {
                AbstractGroovyTaskHandler task = (AbstractGroovyTaskHandler) scriptClass.newInstance();
                task.setPeriod(taskBean.getPeriod());
                return new GroovyScriptTaskHandler(task);
            } else {
                throw new IllegalArgumentException("The given groovy class is not of the right type.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new WioException(e.getMessage(), e);
        }
    }

    public TaskEventHandler createEventHandler(Task taskBean) throws WioException {
        try {
            Class<?> scriptClass = getGroovyClass(taskBean.getEventHandler());
            if (AbstractGroovyEventHandler.class.isAssignableFrom(scriptClass)) {
                AbstractGroovyEventHandler eventHandler = (AbstractGroovyEventHandler) scriptClass.newInstance();
                eventHandler.setPeriod(taskBean.getPeriod());
                return new GroovyScriptEventHandler(eventHandler);
            } else {
                throw new IllegalArgumentException("The given groovy class is not of the right type.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new WioException(e.getMessage(), e);
        }
    }

    private Class<?> getGroovyClass(HandlerBean taskHandler) {
        if (taskHandler.getLanguage() != Language.GROOVYSCRIPT) {
            throw new IllegalArgumentException("The the script must be a groovy script");
        }
        String scriptName = getScriptName(taskHandler);
        GroovyCodeSource gcs = new GroovyCodeSource(taskHandler.getCode(), scriptName, "/groovy/scripts");
        GroovyClassLoader loader = new GroovyClassLoader(GroovyScriptService.class.getClassLoader(), config);
        return (Class<?>) loader.parseClass(gcs, false);
    }

    private String getScriptName(HandlerBean taskHandler) {
        String name = taskHandler.getName();
        return name.replaceAll("[^a-zA-Z]", "_") + ".groovy";
    }


}
