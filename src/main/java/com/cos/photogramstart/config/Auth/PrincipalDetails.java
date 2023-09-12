package com.cos.photogramstart.config.Auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 권란 : 한 개가 아닐 수 있음 (3개 이상의 권한일 수 있음)
	@Override  // 권한을 가져오는 함수
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override  // 계정이 완료가 되었니? - false가 되면 로그인 안됨
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override  // 계정이 잠겼니?
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override  // 계정 비밀번호 만료되지 않았니?
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override  // 계정이 활성화 되어 있니?
	public boolean isEnabled() {
		return true;
	}

}
