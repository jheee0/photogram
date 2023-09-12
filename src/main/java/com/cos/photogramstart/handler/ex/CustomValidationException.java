package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

	// 시리얼 번호는 객체를 구분할 때 사용함. JVM에게 중요하지 우리에게 중요한거 아님
	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;

	}
}
