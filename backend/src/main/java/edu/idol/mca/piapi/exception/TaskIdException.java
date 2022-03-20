/**
 * 
 */
package edu.idol.mca.piapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskIdException is used to handle exceptions on user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskIdException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create TaskIdException object without error message
	 */
	public TaskIdException() {
		super();
	}
	
	/**
	 * Create TaskIdException object with error message
	 */
	public TaskIdException(String errMsg) {
		super(errMsg);
	}
	
	
}