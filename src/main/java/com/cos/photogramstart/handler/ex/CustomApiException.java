package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

	// 시리얼 번호는 객체를 구분할 때 사용함. JVM에게 중요하지 우리에게 중요한거 아님
	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message);
	}
}
