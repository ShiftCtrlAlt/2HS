package com.bem.security;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
	
    @Override  
    protected void configure(AuthenticationManagerBuilder auth)  
            throws Exception {  
//        auth.userDetailsService(userDetailsService())
//        .passwordEncoder(bCryptPasswordEncoder);
//        auth
//        .inMemoryAuthentication()
//            .withUser("user").password("password").roles("USER");
    	auth.userDetailsService(userDetailsService())
        .passwordEncoder(bCryptPasswordEncoder);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RememberMeServices rememberMeServices;
		http.csrf()
			.disable()
			.authorizeRequests()
				.antMatchers("/","/registration","/").permitAll()
				//"/home","/registration","getQuizs","/review","/newPage"
				.anyRequest()
				.authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.deleteCookies("remember-me")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and()
	        	//开启cookie保存用户数据
	        	.rememberMe()
	        	//设置cookie有效期
	        	.tokenValiditySeconds(60 * 60 * 24 * 7)
	        	.rememberMeCookieName("ttttt");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**","/js/lib/**", "/images/**", "/fonts/**");
	}

}