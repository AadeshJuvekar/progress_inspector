/**
 * 
 */
package edu.idol.mca.piapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *TaskNotFoundException is used to handle exceptions on user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create TaskNotFoundException object without error message
	 */
	public TaskNotFoundException() {
		super();
	}
	
	/**
	 * Create TaskNotFoundException object with error message
	 */
	public TaskNotFoundException(String errMsg) {
		super(errMsg);
	}
	
	
}
