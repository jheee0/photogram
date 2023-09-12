package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.Auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.uprofile(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		
		return "user/profile";
	}
	
	@GetMapping("/user/{pageUserId}/update")
	public String update(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		System.out.println("세션정보: "+principalDetails.getUser());
		return "user/update";
	}
	
	@GetMapping("/user/calendar")
	public String cal(){
		return "user/calendar";
	}

}
