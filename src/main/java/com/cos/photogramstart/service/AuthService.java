package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 1. Ioc 등록 2. 트랜잭션 관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write( Insert, Update, Delete) 할 때 사용
	public User join(User user) { // 회원가입 진행
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해쉬로 비밀번호 암호화
		user.setPassword(encPassword); // 비밀번호 저장
		user.setRole("ROLE_USER"); // 관리자는 ROLE_ADMINE
			
		
		User userEntity = userRepository.save(user);
		return userEntity;
	}

}
