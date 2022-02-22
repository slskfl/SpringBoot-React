package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //Object 생성을 위한 디자인 패턴으로 Builder클래스를 따로 구현하지 않아도됨
@NoArgsConstructor // 매개변수가 없는 생성자를 구현해줌
@AllArgsConstructor //클래스의 모든 변수를 매개변수로 받는 생성자를 생성
@Data //getter, setter를 구현해줌
public class TodoDTO {
	
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}

}
