package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{


	//TodoRepository은 JpaRepository를 상속한다. 
	//JpaRepository는 스프링이 알아서 구현한다.
	//TodoRepository는 구현부가 없어도 JpaRepository를 사용할 수 있는가? 
	//AOP는 공통으로 쓰는 메서드를 정의하여 사용할 수 있도록 한다.  JpaRepository는 AOP 기반이다. 
	
	
	//JpaRepository의 메서드가 아닐 경우
	//규칙으로 findBy{컬럼명}({쿼리 값})
	List<TodoEntity> findByUserId(String userId);
	
	//더욱 복잡한 쿼리는 @Query 어노테이션으로 사용한다.
	//?1은 메서드의 매개변수의 순서 위치다. 매개변수가 여려개 있을 경우 구변하기 위함
	//@Query("SELECT * FROM TODO T WHERE T.USERID = ?1")
	
	//@Query("select * from TODO where user = ?1")  //이거 이상함 왜지
	//List<TodoEntity> findByUser(String user);
	
}
