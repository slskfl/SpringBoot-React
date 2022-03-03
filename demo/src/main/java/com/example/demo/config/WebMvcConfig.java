package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	private final long MAX_AGE_SECS = 3600;
		
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") //모든 경로에서
			.allowedOrigins("http://localhost:3001") //Origin이 "http://localhost:3001"일 경우 
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") //해당 메서드를 이용한 요청을 허용한다.
			.allowedHeaders("*") //모든 헤더 정보와
			.allowCredentials(true) //인증에 관한 정보도 허용한다.
			.maxAge(MAX_AGE_SECS); 
	}
}
