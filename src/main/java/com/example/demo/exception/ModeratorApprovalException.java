package com.example.demo.exception;

public class ModeratorApprovalException extends RuntimeException {

	public ModeratorApprovalException() {
		super();
	}
	
	public ModeratorApprovalException(String message) {
		super(message);
	}

	public ModeratorApprovalException(Throwable cause) {
		super(cause);
	}

}