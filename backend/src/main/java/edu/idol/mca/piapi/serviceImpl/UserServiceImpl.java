/**
 * 
 */
package edu.idol.mca.piapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import edu.idol.mca.piapi.domain.Task;
import edu.idol.mca.piapi.domain.User;
import edu.idol.mca.piapi.exception.UserAlreadyExistException;
import edu.idol.mca.piapi.repository.TaskRepository;
import edu.idol.mca.piapi.repository.UserRepository;
import edu.idol.mca.piapi.service.UserService;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	//************************************************************* PRODUCTOWNER OPERATIONS *********************************************************************************

	//-------------------------------------------------------------- USER CRUD OPERATIONS -------------------------------------------------------------------
	@Override
	public User saveUser(User user) {
		
		//Check for null values
		if(user.getLoginName()==null|| user.getPwd()==null||user.getName()==null) {
			throw new NullPointerException("Please fill the required fields");
		}
		
		//Check if user already exists
		if(userRepository.findByLoginName(user.getLoginName())!=null) {
			throw new UserAlreadyExistException("User with "+ user.getLoginName() +" already exists");
		}
		
		//Register New User
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {		
		return userRepository.save(user);
	}

	@Override
	public void deleteUserByLoginName(String loginName) {
		userRepository.deleteByLoginName(loginName);

	}

	@Override
	public List<User> findAll() {		
		return userRepository.findAll();
	}

	@Override
	public User findUserByLoginName(String loginName) {
		return userRepository.findByLoginName(loginName);
	}

	//------------------------------------------------------- TASK OPERATIONS -------------------------------------------------------------------------------------
	@Override
	public List<Task> getAllTasks(HttpSession session) {
		List<Task> tasks=new ArrayList<>();
		User user= userRepository.findByLoginName((String)session.getAttribute("loginName"));
		tasks=user.getTasks();
		return tasks;
	}

	@Override
	public Task getTaskByTaskIdentifier(String taskIdentifier, HttpSession session) {
		Task savedTask=null;
		User user= userRepository.findByLoginName((String)session.getAttribute("loginName"));
		List<Task> tasks=user.getTasks();
		for(Task task:tasks) {
			if(task.getTaskIdentifer().equals(taskIdentifier)) {
				savedTask=task;
			}
		}
		return savedTask;
	}

	//----------------------------------------------------------- USER LOGIN -------------------------------------------------------------------------------
	@Override
	public User authenticateUser(String loginName, String pwd, HttpSession session) {	
		User user= userRepository.findByLoginName(loginName);
		return addUserInSession(user, session);
	}

	private User addUserInSession(User user, HttpSession session) {
		session.setAttribute("userId", user.getId());
		session.setAttribute("userType", user.getUserType());
		session.setAttribute("loginName", user.getLoginName());	
		return user;
	}
	
	//------------------------------------------------------- USERTYPE : CLIENT OPERATIONS -------------------------------------------------------------------------------------
	@Override
	public User addTasktoUser(String loginName, String taskIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAllByUserType("Client");
	}

	//******************************************************************************************************************************************************************
}
