/**
 * 
 */
package edu.idol.mca.piapi.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

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
	public Set<Task> getAllTasks(HttpSession session) {
		Set<Task> tasks=new HashSet<>();
		User user= userRepository.findByLoginName((String)session.getAttribute("loginName"));
		tasks=user.getAssignedTasks();
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
		Set<Task> tasks = user.getAssignedTasks();
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
		for (Task t : user.getAssignedTasks()) {
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
			throw new UserNotFoundException("Product Owner Not Found");
		}
		User leader = userRepository.findByLoginName(leaderLoginName);
		if (leader == null) {
			throw new UserNotFoundException("Team Leader not found");
		}
		if (taskRepository.findByTaskIdentifier(task.getTaskIdentifier()) != null) {
			throw new TaskIdException("Task id " + task.getTaskIdentifier().toUpperCase() + " is already exists");
		}
		task.setTaskIdentifier(task.getTaskIdentifier().toUpperCase());
		task.setProgress("Pending");
		Set<Task> ownerTaskList = owner.getAssignedTasks();
		ownerTaskList.add(task);		
		owner.setAssignedTasks(ownerTaskList);
		
		Set<Task> leaderTaskList = leader.getAssignedTasks();
		leaderTaskList.add(task);
		leader.setAssignedTasks(leaderTaskList);
		
		Set<User> assignedUsers =task.getUsers();
		assignedUsers.add(owner);
		assignedUsers.add(leader);
		task.setUsers(assignedUsers);
		Task newTask = taskRepository.save(task);
		userRepository.save(leader);
		userRepository.save(owner);
		return newTask;
	}

	@Override
	public void deleteTask(String taskIdentifier) {		
		Task task = taskRepository.findByTaskIdentifier(taskIdentifier.toUpperCase());			
		if (task == null) {
			throw new TaskIdException("Task with Identifer " + taskIdentifier.toUpperCase() + " doesn't exist");
		}
		Set<User> users = task.getUsers();
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); iterator.hasNext()) {		    
			if(iterator.next()==null) {
				break;
			}
			User user = iterator.next();		
		    task.removeUser(user);
		    updateUser(user);
			log.info(user.getLoginName());
		}
		taskRepository.delete(task);
	}

	@Override
	public Task updateTask(Task task) {
		// checking for null task
		if (task.getTaskIdentifier() == null) {
			throw new NullPointerException("Please Fill the Required Fields");
		}
		// finding Task with provided taskIdentifier
		Task oldTask = taskRepository.findByTaskIdentifier(task.getTaskIdentifier());
		if (oldTask == null) {
			throw new TaskIdException("Task with taskIdentifier : " + task.getTaskIdentifier() + " does not exists");
		}
		task.setId(oldTask.getId());
		oldTask.setTitle(task.getTitle());
		oldTask.setDescription(task.getDescription());
		oldTask.setProgress(task.getProgress());
		// returning updated Task
		return taskRepository.save(oldTask);
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
/*	@Override
	public User assignUser(String loginName, String taskIdentifier) {
		
	}
	*/

	@Override
	public Task addRemark(Remark remark, String taskIdentifier) {
		if (taskIdentifier == null || remark == null) {
			throw new NullPointerException("Please Fill the Required Fields");
		}
		Task task = taskRepository.findByTaskIdentifier(taskIdentifier);
		
		if (task.getTaskIdentifier() == null) {
			throw new TaskIdException("Task with Identifer" + taskIdentifier.toUpperCase() + " doesn't exist");
		}
		remark.setTask(task);
		Set<Remark> remarkList = new HashSet<>();
		if (task.getRemarks() != null) {
			remarkList = task.getRemarks();
		}
		remarkList.add(remark);
		task.setRemarks(remarkList);
		remarkRepository.save(remark);
		taskRepository.save(task);
		return task;
	}

	@Override
	public User addTasktoUser(String loginName, String taskIdentifier) {
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
		Set<Task> listOfTask = new HashSet<>();
		if(user.getAssignedTasks()!=null) {
			listOfTask=user.getAssignedTasks();
		}		
		if(listOfTask.contains(task)) {
			return user;
		}
		//assign user to task
		listOfTask.add(task);
		user.setAssignedTasks(listOfTask);
		userRepository.save(user);
		return user;
	}

	@Override
	public Task updateTaskStatus(String taskIdentifier, String loginName, Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task assignDeveloper(String taskIdentifier, String loginName) {
		// get developer
				User developer = userRepository.findByLoginName(loginName);
				// check if available or not
				// throw exception not found
				if (developer == null) {
					throw new UserNotFoundException("Developer with Identifer " + loginName + " doesn't exist");
				}
				Task task = taskRepository.findByTaskIdentifier(taskIdentifier);
				if (task == null) {
					throw new TaskIdException("Task with Identifer " + taskIdentifier.toUpperCase() + " doesn't exist");
				}
//				Set<Task> developerTaskList = developer.getAssignedTasks();
				Set<User> assignedUsers =task.getUsers();
				
				for (Iterator<User> iterator = assignedUsers.iterator(); iterator.hasNext(); ) {
				    if(iterator.hasNext()) {
					User oldDeveloper = iterator.next();
				    if (oldDeveloper.getUserType().equals("Developer")){
				    	task.removeUser(oldDeveloper);
					}	}
				}							
				task.addUser(developer);
				userRepository.save(developer);
//				task.setUsers(assignedUsers);
				
				return taskRepository.save(task);
	}		

	//******************************************************************************************************************************************************************
}
