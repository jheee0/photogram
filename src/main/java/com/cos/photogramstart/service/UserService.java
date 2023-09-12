package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final SubscribeRepository subscribeRepository;
	
	@Transactional
	public UserProfileDto uprofile(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();
		
		// Select * from image where userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()-> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
		System.out.println("===========================");
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 1
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeState(subscribeState==1);
		
		return dto;
	}
	
	@Transactional
	public User uupdate(int id, User user) {
		// 영속화
		User userEntity = userRepository.findById(id).get(); //1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 exception 발동. orElseThrow
				
		// 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
		userEntity.setName(user.getName());
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		return userEntity;
	} // 더티체킹이 일어나서 더티체킹됨.

}
