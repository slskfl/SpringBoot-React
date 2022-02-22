package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //http와 관련된 코드 및 요청/응답 매핑을 스피링이 알아서 해줌
@RequestMapping("test")
public class TestController {
	
	// URL로 Get localhost:8080/test를 호출할 경우 testController()가 호출된다.
	@GetMapping //메서드의 리소스와 HTTP메서드를 지정한다.
	public String testController() {
		return "Hello World!";
	
	}
	
	// URL로 Get localhost:8080/test/testGetMapping를 호출할 경우 testController()가 호출된다.
		@GetMapping( "/testGetMapping") //메서드의 리소스와 HTTP메서드를 지정한다.
		public String testControllerWithPath() {
			return "Hello World! testGetMapping!!";
		
		}
}
