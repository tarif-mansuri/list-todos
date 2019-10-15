//$Id$
package com.docable.todo.exceptions;

public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169930440159980623L;

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable reason) {
		super(reason);
	}

	public CustomException(String message, Throwable reason) {
		super(message, reason);
	}
}
