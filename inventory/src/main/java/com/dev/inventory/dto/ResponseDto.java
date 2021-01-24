package com.dev.inventory.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDto {
	
	private String code;
	private String message;
	private Object payload;
	private List<String> errorMessages;
	
	public ResponseDto() {
		this.errorMessages = new ArrayList<String>();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	

}
