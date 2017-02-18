package nl.openweb.iot.dashboard.cucumber.stepdefs;

import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import nl.openweb.iot.dashboard.DashboardAppConfig;

@WebAppConfiguration
@ContextConfiguration(classes = DashboardAppConfig.class, loader = SpringApplicationContextLoader.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
