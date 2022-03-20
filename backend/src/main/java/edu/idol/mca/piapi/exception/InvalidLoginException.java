/**
 * 
 */
package edu.idol.mca.piapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *InvalidLoginException is used to handle exceptions on user.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create InvalidLoginException object without error message
	 */
	public InvalidLoginException() {
		super();
	}
	
	/**
	 * Create InvalidLoginException object with error message
	 */
	public InvalidLoginException(String errMsg) {
		super(errMsg);
	}
	
	
}
