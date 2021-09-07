package com.example.ebanking.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String FILTER_TYPE = "error";
    private static final String THROWABLE_KEY = "throwable";
    private static final int FILTER_ORDER = -1;

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
        final RequestContext context = RequestContext.getCurrentContext();
        final Object throwable = context.getThrowable();

        if (throwable instanceof ZuulException) {
            final ZuulException zuulException = (ZuulException) throwable;
            logger.error("Zuul failure detected: " + zuulException.getMessage());

            context.remove(THROWABLE_KEY);

            String requestURI = context.getRequest().getRequestURI();
            context.setResponseBody("There is no server instance that responds to the request " + requestURI);
            context.getResponse().setContentType("application/json");
            context.setResponseStatusCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
        }
        return null;
    }
}
