package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.demo.service.TodoService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j //로그 어노테이션
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "todo")
//@Entity(name = "TodoEntity") 엔티티에 이름을 부여할 경우
//DB의 Todo 테이블에 매칭이된다는 것이다.
//테이블 명이 없을 경우 엔티티명으로, 엔티티명도 없으면 클래스명으로 테이릅 이름을 설정
@Table
public class TodoEntity {
	@Id //기봍키를 의미
	@GeneratedValue(generator = "system-uuid") //ID를 자동으로 생성하는 어노테이션이다. (generator = "system-uuid")은 ID를 생성하는 방식을 정의한다.
	@GenericGenerator(name = "system-uuid", strategy = "uuid") //기본으로 제공하는 generator가 아닌 개발자가 생성해서 사용하겠다는 의미로 사용되는 어노테이션이다.
	//문자열인 UUID를 사용하는 system-uuid라는 이름의 generator를 만들었고, 이 generator이 @GeneratedValue가 참조해 사용한다.
	private String id;
	private String userId;
	private String title;
	private boolean done;
	
	
}
