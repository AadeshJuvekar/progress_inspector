/**
 * 
 */
package edu.idol.mca.piapi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import edu.idol.mca.piapi.domain.Task;
import edu.idol.mca.piapi.domain.User;

/**
 *
 */
public interface UserService {

	public User saveUser(User user);
	
	public User updateUser(User user);
	
	public void deleteUserByLoginName(String loginName);
	
	public List<User> findAll();
	
	public User findUserByLoginName(String loginName);
	
	public List<Task> getAllTasks(HttpSession session);
	
	public Task getTaskByTaskIdentifier(String taskIdentifier, HttpSession session);
	
	public User authenticateUser(String loginName, String pwd, HttpSession session);
	
	public User addTasktoUser(String loginName, String taskIdentifier);
	
	//public List<User> getAllUsers();

	public List<User> getAllUsersByUserType(String userType);
}
