package com.github.bilak.oauth2poc.gateway.configuration;

import com.github.bilak.oauth2poc.gateway.filter.CsrfHeaderFilter;
import com.github.bilak.oauth2poc.gateway.filter.DynamicOauth2ClientContextFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Created by lvasek on 26/05/16.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	@Primary
	public OAuth2ClientContextFilter dynamicOauth2ClientContextFilter() {
		return new DynamicOauth2ClientContextFilter();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*
		http
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.httpBasic().disable()
				// TODO improve /uaa/** and find all targets which should be protected
				.authorizeRequests().antMatchers("/uaa/**", "/login", "/", "/index.html").permitAll().anyRequest().authenticated()
				.and()
				.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(csrfTokenRepository())
				//.csrf().csrfTokenRepository(csrfTokenRepository())
				.and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
				*/
		http.authorizeRequests().antMatchers("/uaa/**", "/login").permitAll().anyRequest().authenticated()
				.and()
				.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(csrfTokenRepository())
				//.csrf().csrfTokenRepository(csrfTokenRepository())
				.and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				.logout().permitAll()
				.logoutSuccessUrl("/");
	}

	private RequestMatcher csrfRequestMatcher() {
		return new RequestMatcher() {
			// Always allow the HTTP GET method
			private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|OPTIONS|TRACE)$");

			// Disable CSRF protection on the following urls:
			private final AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/uaa/**") };

			@Override
			public boolean matches(HttpServletRequest request) {
				if (allowedMethods.matcher(request.getMethod()).matches()) {
					return false;
				}

				for (AntPathRequestMatcher matcher : requestMatchers) {
					if (matcher.matches(request)) {
						return false;
					}
				}
				return true;
			}
		};
	}

	@Bean
	CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(CsrfHeaderFilter.CSRF_TOKEN_NAME);
		return repository;
	}

	@Bean
	Filter csrfHeaderFilter() {
		return new CsrfHeaderFilter();
	}

	@Bean
	FilterRegistrationBean filterRegistrationBean() {
		return new FilterRegistrationBean(new CsrfFilter(csrfTokenRepository()));
	}
}
