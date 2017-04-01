package nl.openweb.iot.dashboard.config;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZuulHostFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ZuulHostFilter.class);

    private static final String HEADER_HOST = "Host";

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.getZuulRequestHeaders().put(HEADER_HOST, request.getServerName());
        return null;
    }
}
