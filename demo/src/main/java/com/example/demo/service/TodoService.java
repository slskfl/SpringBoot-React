package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 어노테이션
@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	public String testService() {
	
		//TodoEntity 생성
		TodoEntity todoEntity = TodoEntity.builder().title("todo item").build();
		//TodoEntity 저장
		todoRepository.save(todoEntity);
		//TodoEntity 검색
		TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> retrieve(final String uerId){
		return todoRepository.findByUserId(uerId);
	}
	
	public List<TodoEntity> create(final TodoEntity entity){
		
		vaildate(entity);
		
		//저장
		todoRepository.save(entity);
		
		log.info("Entity Id : {} is saved", entity.getId());
		
		return todoRepository.findByUserId(entity.getUserId());
	}
	
	public List<TodoEntity> update(final TodoEntity entity){
		//Optional과 Lamda식 이용 
		vaildate(entity);
		
		//넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않은 엔티티는 업데이트할 수 없다.
		final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
		
		original.ifPresent(todo -> {
			//반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			//새로운 값을 저장한다.
			todoRepository.save(todo);
		});
		
		return retrieve(entity.getUserId());
	}
	
	
	public List<TodoEntity> delete(final TodoEntity entity){
		vaildate(entity);
		
		try {
			todoRepository.delete(entity);
		}catch(Exception e) {
			log.error("error deleting entity", entity.getId());
			
			throw new RuntimeException("error deleting entity --"+ entity.getId());
		}
		return retrieve(entity.getUserId());
	}
	
	private void vaildate(final TodoEntity entity) {
		//검증 : 넘어온 엔티티가 유효한지를 확인하는 과정
		if(entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}

		if(entity.getUserId() == null) {
			log.warn("Unkown user");
			throw new RuntimeException("Unkown user");
		}
		
	}
}
