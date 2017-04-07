package nl.openweb.iot.dashboard.service.script;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import nl.openweb.iot.dashboard.domain.HandlerBean;
import nl.openweb.iot.dashboard.domain.Task;
import nl.openweb.iot.dashboard.domain.enumeration.Language;
import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.scheduling.ScheduledTask;
import nl.openweb.iot.wio.scheduling.TaskEventHandler;

@Service
public class JsScriptService {

    private NashornScriptEngineFactory factory = new NashornScriptEngineFactory();



    public ScheduledTask createScheduledTask(Task taskBean) throws WioException {
        try {
            validate(taskBean.getTaskHandler());
            NashornScriptEngine scriptEngine = (NashornScriptEngine) factory.getScriptEngine(new JsSandboxFilter());
            scriptEngine.eval(taskBean.getTaskHandler().getCodeAsString());
            JSObject jsObject = (JSObject) scriptEngine.invokeFunction("constructor", (int) Math.round(taskBean.getPeriod() * 60), JsTaskHandler.LOG);

            return new JsTaskHandler(jsObject);

        } catch (ScriptException | NoSuchMethodException e) {
            throw new WioException(e.getMessage(), e);
        }
    }



    public TaskEventHandler createEventHandler(Task taskBean) throws WioException {
        try {
            validate(taskBean.getEventHandler());
            NashornScriptEngine scriptEngine = (NashornScriptEngine) factory.getScriptEngine(new JsSandboxFilter());
            scriptEngine.eval(taskBean.getEventHandler().getCodeAsString());
            JSObject jsObject = (JSObject) scriptEngine.invokeFunction("constructor", JsTaskHandler.LOG);
            return new JsEventHandler(jsObject);

        } catch (ScriptException | NoSuchMethodException e) {
            throw new WioException(e.getMessage(), e);
        }
    }

    private void validate(HandlerBean handlerBean) {
        Language language = handlerBean.getLanguage();
        if (language != Language.JAVASCRIPT) {
            throw new IllegalArgumentException("The script must be Javascript");
        }
        if (StringUtils.isBlank(handlerBean.getCodeAsString())) {
            throw new IllegalArgumentException("The script should not be blank");
        }
    }
}
