/**
 * 
 */
package edu.idol.mca.piapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *UserAlreadyExistException is used to handle exceptions on user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create UserAlreadyExistException object without error message
	 */
	public UserAlreadyExistException() {
		super();
	}
	
	/**
	 * Create UserAlreadyExistException object with error message
	 */
	public UserAlreadyExistException(String errMsg) {
		super(errMsg);
	}
	
	
}
