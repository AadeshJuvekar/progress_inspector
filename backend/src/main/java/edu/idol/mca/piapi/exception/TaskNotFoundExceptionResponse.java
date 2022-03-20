/**
 * 
 */
package edu.idol.mca.piapi.exception;

/**
 *	 TaskNotFoundException is used to handle exceptions on user.
 */
public class TaskNotFoundExceptionResponse {
	
	private String loginName;

	public TaskNotFoundExceptionResponse(String loginName) {
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
