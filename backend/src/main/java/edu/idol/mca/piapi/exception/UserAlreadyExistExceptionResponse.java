/**
 * 
 */
package edu.idol.mca.piapi.exception;

/**
 *	 UserAlreadyExistException is used to handle exceptions on user.
 */
public class UserAlreadyExistExceptionResponse {
	
	private String loginName;

	public UserAlreadyExistExceptionResponse(String loginName) {
		super();
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}	

}
