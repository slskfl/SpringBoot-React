package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

import lombok.Builder;

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
		return "Hello World! testGetMapping!! 바로바로";
	
	}
	
	//URL에 매개변수를 넘기는 방법  (required = false)은 해당 매개변수가 꼭 필수값을 아니라는 뜻이다. url에 값만 넣는다. test/테스트
	@GetMapping("/pathVariables/{value}")
	public String testControllerWithPathVariables(@PathVariable(required = false) String value) {
		return "value = " + value;
	}
	
	//URL에 매개변수를 넘기는 방법 2, url에 매개변수명과 값을 함께 넣는다. test/testRequestParam?id=123
	@GetMapping("/testRequestParam")
	public String testControllerWithRequestParam(@RequestParam(required = false)  int param) {
		return "param = " + param;
	}
	
	//RequestBody 요청의 json 형태의 뮨자열을 받아 TestRequestBodyDTO의 형태로 오브젝트를 변환하는 과정을 거친다.
	@GetMapping("/testRequestBody")
	public String testControllerRequsetBody(@RequestBody TestRequestBodyDTO testRequsetBodyDTO) {
		return "ID = " + testRequsetBodyDTO.getId() + ", msg = " + testRequsetBodyDTO.getMsg(); 
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String>  testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("ResponseDTO는 메서드가 리터할 때 스프링은 리턴된 오브젝트를 JSON 형태로 바꾸고 HttpsResonse에 담아 반환한다.");
//		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
	
		String errorMsg = "에러";
//		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().error(errorMsg).build();
//		responseDTO.setData(list);
		
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).error(errorMsg).build();
		return responseDTO;
	}
	
	@GetMapping("/testREsponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("ResponseEntity는 HTTP응답의 바디뿐만 아니라 여러 다른 매개변수들을 반환할 때 사용한다.");
		
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).error("에러입니당").build();
		
		//return ResponseEntity.badRequest().body(responseDTO); // status가 400
		return ResponseEntity.ok().body(responseDTO); //status가 200
	}
}
