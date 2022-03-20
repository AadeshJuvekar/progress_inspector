/**
 * 
 */
package edu.idol.mca.piapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserNotFoundException is used to handle exceptions on user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create UserNotFoundException object without error message
	 */
	public UserNotFoundException() {
		super();
	}
	
	/**
	 * Create UserNotFoundException object with error message
	 */
	public UserNotFoundException(String errMsg) {
		super(errMsg);
	}
	
	
}
