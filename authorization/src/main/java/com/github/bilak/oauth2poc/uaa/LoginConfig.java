package com.github.bilak.oauth2poc.uaa;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by lvasek on 20/05/16.
 */
@Configuration
@Order(-20)
class LoginConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin().loginPage("/login").permitAll()
				.and()
				.requestMatchers()
				.antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access")
				.and()
				.authorizeRequests()
				.anyRequest().authenticated();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER")
				.and()
				.withUser("foo").password("bar").roles("USER");
	}
}
