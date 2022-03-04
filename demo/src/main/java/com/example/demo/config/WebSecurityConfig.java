package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		//http는 시큐리티 설정을 위한 오브젝트이다. web.xml에 대신에 HttpSecurity를 통해 시큐리티 설정을 한다.
		http.cors() //cors를 설정 WebMvcConfig에서 이미 정의한 내용
		.and() 
		.csrf().disable() //crsf는 사용하지 않으므로 disable 처리
		.httpBasic().disable() //basic인증이 아닌 token인증이라서 disable 처리
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sesseion 기반이 아님
		.and()
		.authorizeHttpRequests().antMatchers("/", "/auth/**").permitAll() // '/'와 '/auth/**' 경로는 인증없이 허용
		.anyRequest().authenticated();// '/'와 '/auth/**' 경로 외에는 인증 필요
		
		/*
		 * filter를 등록하여 매 요청마다 CorsFilter를 실행한 후에 jwtAhtuenticationFilter를 실행
		 * addFilterAfter 메서드를 실행하는 것이 아닌 실행 순서를 정해주는 것*/
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
	}
}
