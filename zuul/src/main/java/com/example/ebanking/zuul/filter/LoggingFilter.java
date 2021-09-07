package com.example.ebanking.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String FILTER_TYPE = "pre";
    private static final int FILTER_ORDER = 1;

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestedURI = ctx.getRequest().getRequestURI();

        logger.info("Requested URI: " + requestedURI);

        return null;
    }
}
