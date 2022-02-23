package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;
@Slf4j //로그 어노테이션
@RestController //내부에 @Component 어노테이션을 갖고 있다. 따라서 @Service와 @RestController도 스프링이 관리한다.
@RequestMapping("todo")
public class TodoController {
	//스프링은 TodoController를 생성할 때 TodoService에 @Autowired이 붙어있는 것을 확인한다.
	//@Autowired가 알아서 빈을 찾을 다음 그 빈을 이 인스턴스 변수에 연결하라는 뜻이다.
	//즉 TodoController를 초기화할 때 TodoService를 초기화 또는 검색하여 TodoController에 주입해준다.
	@Autowired
	private TodoService service;
	
	@GetMapping("/testTodo")
	public ResponseEntity<?> todoResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("해봅시다.");
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(responseDTO);
	}

	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		
		ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId = "temporary-userid";
			
			//엔티티로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//id를 null로 초기화, 생성 당시에는 id가 없어야된다.
			entity.setUserId(null);
			
			//위에서 생성한 사용자 아이디를 넣어준다.
			entity.setUserId(temporaryUserId);
			
			//서비스를 이용하여 Todo 엔티티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			
			//자바 스트림을 이용하여 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//변환된 TodoDTO 리스트릴 이용하여 ResponseDTO를 초기화한다.
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(responseDTO);
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary-userid";
		
		//서비스 메서드의 retrieve() 메세드를 사용해서 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(responseDTO);
	
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
		String temporaryUserId = "temporary-userid";
		
		//dto를 entity에 변환한다.
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		//id를 임시 id로 초기화한다. 
		entity.setUserId(temporaryUserId);
		
		//서비스를 이용하여 entity를 업데이트한다.
		List<TodoEntity> entities = service.update(entity);
		
		//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(responseDTO);		
		
	}
	
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId = "temporary-userid";
			
			//dto를 entity에 변환한다.
			TodoEntity entity = TodoDTO.toEntity(dto);
			
			//id를 임시 id로 초기화한다. 
			entity.setUserId(temporaryUserId);
			
			//서비스를 이용하여 entity를 삭제한다.
			List<TodoEntity> entities = service.delete(entity);
			
			//자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//변환된 TodoDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(responseDTO);		
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
