package com.dove.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jh on 2017/5/15.
 */
public class AccessFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Override public String filterType() {
		return "pre";
	}

	@Override public int filterOrder() {
		return 0;
	}

	@Override public boolean shouldFilter() {
		return true;
	}

	@Override public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		logger.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

		Object accessToken = request.getParameter("accessToken");

		if (accessToken == null) {
			logger.warn("access token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			return null;
		}

		logger.info("access token ok");
		return null;
	}
}