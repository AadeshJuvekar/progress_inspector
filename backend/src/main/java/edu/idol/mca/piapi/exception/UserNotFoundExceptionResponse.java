/**
 * 
 */
package edu.idol.mca.piapi.exception;

/**
 *	 UserNotFoundExceptionResponse is used to handle exceptions on user.
 */
public class UserNotFoundExceptionResponse {
	
	private String loginName;

	public UserNotFoundExceptionResponse(String loginName) {
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
