package com.github.bilak.oauth2poc.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lvasek on 24/05/16.
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {

	public static final String CSRF_TOKEN_NAME = "X-CSRF-TOKEN";
	public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (csrf != null) {
			String headerToken = request.getHeader(CSRF_TOKEN_NAME);
			String token = csrf.getToken();
			String requestURI = request.getRequestURI();
			/*
			if (requestURI.startsWith("/uaa")) {
				Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
				if (cookie == null || token != null && !token.equals(cookie.getValue())) {
					cookie = new Cookie(CSRF_COOKIE_NAME, token);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			*/
			if (StringUtils.isEmpty(headerToken) || token != null && !token.equals(headerToken)) {
				response.setHeader(CSRF_TOKEN_NAME, token);
			}
		}

		filterChain.doFilter(request, response);
	}
}
