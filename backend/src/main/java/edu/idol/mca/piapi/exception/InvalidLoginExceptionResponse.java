/**
 * 
 */
package edu.idol.mca.piapi.exception;

/**
 *	 InvalidLoginException is used to handle exceptions on user.
 */
public class InvalidLoginExceptionResponse {
	
	private String loginName;

	public InvalidLoginExceptionResponse(String loginName) {
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
