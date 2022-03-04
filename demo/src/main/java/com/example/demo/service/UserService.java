package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		
		if(userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		
		final String email = userEntity.getEmail();
		
		if(userRepository.existsByEmail(email)) {
			log.warn("이미 등록된 eamil  {} 입니다.", email);
			throw new RuntimeException("이미 등록된 이메일입니다.");
		}
		
		return userRepository.save(userEntity);
	}
	
	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		
		final UserEntity originalUser = userRepository.findByEmail(email);
		
		//같은 값을 인터딩하더라고 할 때마다 값이 다르므로 비교할 수 없어, matches()를 사용하여 두 값이 일치하는지 여부만 확인한다.
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		
		return null;
	}
}
