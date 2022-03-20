/**
 * 
 */
package edu.idol.mca.piapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.idol.mca.piapi.domain.Remark;
import edu.idol.mca.piapi.domain.Task;
import edu.idol.mca.piapi.domain.User;
import edu.idol.mca.piapi.exception.LoginException;
import edu.idol.mca.piapi.exception.TaskIdException;
import edu.idol.mca.piapi.exception.TaskNotFoundException;
import edu.idol.mca.piapi.exception.UserAlreadyExistException;
import edu.idol.mca.piapi.exception.UserNotFoundException;
import edu.idol.mca.piapi.repository.RemarkRepository;
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

	@Autowired
	private RemarkRepository remarkRepository;
	
	
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
		User oldUser =null;
		//Check for Null Values
		if (user.getLoginName() == null) {
			throw new NullPointerException("Please Fill the Required Fields");
		}
		// Check if user exists
		if ((oldUser=userRepository.findByLoginName(user.getLoginName())) == null) {
			throw new UserNotFoundException("User with loginName : " + user.getLoginName() + " does not exists");
		}
		// update user object
		user.setId(oldUser.getId());
		oldUser = user;
		return userRepository.save(oldUser);
	}

	@Override
	public void deleteUserByLoginName(String loginName) {
		User user = null;
		//Check for Null Values
		if (loginName == null) {
			throw new NullPointerException("Please Provide Login Name");
		}
		//Check if User exists
		if ((user = userRepository.findByLoginName(loginName)) == null) {
			throw new UserNotFoundException("User with loginName : " + loginName + " does not exists");
		}
		//Delete User
		userRepository.delete(user);

	}

	@Override
	public List<User> findAll() {		
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new UserNotFoundException("No User Found");
		}
	}

	@Override
	public User findUserByLoginName(String loginName) {
		try {
			return userRepository.findByLoginName(loginName);
		} catch (Exception e) {
			throw new UserNotFoundException("User with loginName : " + loginName + " does not exist");
		}
	}
	/*@Override
	public List<User> getAllUsers() {
		return userRepository.findAllByUserType("Client");
	}*/

	@Override
	public List<User> getAllUsersByUserType(String userType) {
		try {
			return userRepository.findByUserType(userType);
		} catch (Exception e) {
			throw new UserNotFoundException("Clients not available");
		}		
	}
	
	//------------------------------------------------------- TASK OPERATIONS -------------------------------------------------------------------------------------
	@Override
	public List<Task> getAllTasks(HttpSession session) {
		List<Task> tasks=new ArrayList<>();
		User user= userRepository.findByLoginName((String)session.getAttribute("loginName"));
		tasks=user.getTasks();
		if (tasks==null) {
			throw new TaskNotFoundException("Tasks not available");
		}
		return tasks;
	}

	@Override
	public Task getTaskByTaskIdentifier(String taskIdentifier, HttpSession session) {
		Task savedTask=null;
		
		if(taskIdentifier==null) {
			throw new NullPointerException("Please Provide Task Identifier");
		}
		User user = userRepository.findByLoginName((String) session.getAttribute("loginName"));
		List<Task> tasks = user.getTasks();
		for (Task task : tasks) {
			if (task.getTaskIdentifier().equals(taskIdentifier)) {
				savedTask= task;
			}
		}
		if(savedTask==null) {
		throw new TaskNotFoundException("Task with id : '" + taskIdentifier + "' does not exists");
		}
		return savedTask;
	}
	@Override
	public Task updateTaskStatus(String taskIdentifier,String loginName,String progress) {
		if (loginName == null || taskIdentifier == null || progress == null) {
			throw new NullPointerException("Please Fill the Required Fields");
		}
		User user = null;
		if ((user=userRepository.findByLoginName(loginName) )== null) {
			throw new UserNotFoundException("user with " + loginName + " does not exist");
		}
		// Task task1 = taskRepository.findByTaskIdentifier(taskIdentifier);
		Task task1 = new Task();
		for (Task t : user.getTasks()) {
			if (t.getTaskIdentifier().equals(taskIdentifier)) {
				task1 = t;
			}
		}
		if (task1.getTaskIdentifier() == null) {
			throw new TaskIdException("Task with Identifer" + taskIdentifier.toUpperCase() + " doesn't exist");
		}
		task1.setProgress(progress);
		return taskRepository.save(task1);
	}

	@Override
	public Task createTask(Task task, String ownerLoginName, String leaderLoginName) {
		User owner = userRepository.findByLoginName(ownerLoginName);
		if (owner == null) {
			// TODO User Not Found Exception
			throw new UserNotFoundException("Product Owner Not Found");
		}
		User leader = userRepository.findByLoginName(leaderLoginName);
		if (leader == null) {
			throw new UserNotFoundException("Team Leader not found");
		}
		task.setTaskIdentifier(task.getTaskIdentifier().toUpperCase());
		task.setProgress("Pending");
		List<Task> userTaskList = owner.getTask();
		userTaskList.add(task);
		owner.setTasks(userTaskList);
		task.setUser(owner);
		if (taskRepository.findByTaskIdentifier(task.getTaskIdentifier()) != null) {
			throw new TaskIdException("Task id " + task.getTaskIdentifier().toUpperCase() + " is already available");
		}
		Task newTask = taskRepository.save(task);
		List<Task> teamLeaderTaskList = teamLeader.getTask();
		teamLeaderTaskList.add(task);
		teamLeader.setTask(teamLeaderTaskList);
		task.setTeamLeader(teamLeader);
		teamLeaderRepository.save(teamLeader);
		userRepository.save(user);
		return newTask;
	}

	@Override
	public void deleteTask(String taskIdentifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task updateTask(Task task) {
		//if user only status update
		//if team leader whole task update
		
		
		return null;
	}

	//----------------------------------------------------------- USER LOGIN -------------------------------------------------------------------------------
	@Override
	public User authenticateUser(String loginName, String pwd, HttpSession session) {	
		User user = null;
		//Check for null values
		if (loginName == null || pwd == null) {
			throw new LoginException("Please Enter Credentials");
		}
		//Check if User exists
		if ((user = userRepository.findByLoginName(loginName)) == null) {
			throw new UserNotFoundException("User with loginName : " + loginName + " does not exist");
		}
		//Check for password
		if (user.getPwd().equals(pwd)) {
			addUserInSession(user, session);
			return user;
		}else {
			throw new LoginException("Invalid Credentials");
		}
	}

	private User addUserInSession(User user, HttpSession session) {
		session.setAttribute("userId", user.getId());
		session.setAttribute("userType", user.getUserType());
		session.setAttribute("loginName", user.getLoginName());	
		return user;
	}
	
	//------------------------------------------------------- USERTYPE : CLIENT OPERATIONS -------------------------------------------------------------------------------------
	@Override
	public User assignUser(String loginName, String taskIdentifier) {
		User user = null;
		Task task = null;
		//Check for null values
		if(loginName==null || taskIdentifier==null) {
			throw new NullPointerException("Please Provide Required Fields");

		}
		//Check if user exist
		if ((user = userRepository.findByLoginName(loginName)) == null) {
			throw new UserNotFoundException("User with loginName : " + loginName + " does not exists");
		}		
		
		//check if task exists 
		if ((task = taskRepository.findByTaskIdentifier(taskIdentifier)) == null) {
			throw new TaskNotFoundException("Task with id : " + taskIdentifier + " does not exists");
		}
		List<Task> listOfTask = new ArrayList<>();
		if(user.getTasks()!=null) {
			listOfTask=user.getTasks();
		}		
		if(listOfTask.contains(task)) {
			return user;
		}
		//assign user to task
		listOfTask.add(task);
		user.setTasks(listOfTask);
		userRepository.save(user);
		return user;
	}

	

	@Override
	public Remark addRemark(Remark remark, String taskIdentifier) {
		if (taskIdentifier == null || remark == null) {
			throw new NullPointerException("Please Fill the Required Fields");
		}
		Task task = taskRepository.findByTaskIdentifier(taskIdentifier);
		
		if (task.getTaskIdentifier() == null) {
			throw new TaskIdException("Task with Identifer" + taskIdentifier.toUpperCase() + " doesn't exist");
		}
		remark.setTask(task);
		List<Remark> remarkList = new ArrayList<>();
		if (task.getRemarks() != null) {
			remarkList = task.getRemarks();
		}
		remarkList.add(remark);
		task.setRemarks(remarkList);
		remarkRepository.save(remark);
		taskRepository.save(task);
		return remark;
	}

	@Override
	public User addTasktoUser(String loginName, String taskIdentifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task updateTaskStatus(String taskIdentifier, String loginName, Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User assignDeveloper(String taskIdentifier, String loginName) {
		// TODO Auto-generated method stub
		return null;
	}	

	//******************************************************************************************************************************************************************
}
