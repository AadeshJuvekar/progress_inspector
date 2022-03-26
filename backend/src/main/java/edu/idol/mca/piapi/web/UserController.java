
package edu.idol.mca.piapi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.idol.mca.piapi.domain.Remark;
import edu.idol.mca.piapi.domain.Task;
import edu.idol.mca.piapi.domain.User;
import edu.idol.mca.piapi.service.UserService;
import edu.idol.mca.piapi.serviceImpl.MapValidationErrorService;

/**
 *User Controller is used to handle the requests and responses
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapValidationErrorService errorService;

		/* |-------------------------------------------------| LOGIN AUTHENTICATION |--------------------------------------------------------------| */
	
	/**
		 * Method for handling user login and creating session.
		 * 
		 * @param user
		 * @param result       contains the result and error of validation
		 * @param session      Creates New Session
		 * @return Response Entity with logged In user with HTTP Status
		 */
		@PostMapping("/login")
		public ResponseEntity<?> handleUserLogin(@RequestBody User user, BindingResult result,
				HttpSession session) {
			ResponseEntity<?> errorMap = errorService.mapValidationError(result);
			if (errorMap != null) {
				return errorMap;
			}
			User loggedInOwner = userService.authenticateUser(user.getLoginName(),
					user.getPwd(), session);
			//return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
			return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		}


	/**
	 * Method for logging out User and terminating existing session.
	 * @param session get current session details
	 * @return Logout Success message
	 */
	@GetMapping("/logout")
	public ResponseEntity<?> handleUserLogout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("Logout Successful", HttpStatus.OK);
	}

	/* |---------------------------------------------| USER CRUD OPERATIONS |----------------------------------------------------------| */


	/**
	 * Method for Registration of new user and storing data to database.
	 * @param user Data collected to save
	 * @param result contains the result and errors of validation
	 * @return saved user in database
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		User savedUser= userService.saveUser(user);

		//return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		return new ResponseEntity<String>("Registration Successful",HttpStatus.OK);
	}
	
	
	/**
	 * Method for updating user in database.
	 * @param user Data collected to update
	 * @param result contains the result and errors of validation
	 * @return saved user in database
	 */
	@PatchMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user,HttpSession session, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;		
		if(session.getAttribute("loginName")!=null && session.getAttribute("loginName").equals(user.getLoginName())) {
			User savedUser= userService.updateUser(user);
			return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have access !!!",HttpStatus.UNAUTHORIZED);
		
	}
	
	/**
	 * Method to delete user by loginName
	 * 
	 * @param loginName of the user
	 */

	@DeleteMapping("/{loginName}")
	public ResponseEntity<?> deleteUser(@PathVariable String loginName, HttpSession session) {
		if (session.getAttribute("loginName") != null && session.getAttribute("loginName").equals(loginName)) {
		
		userService.deleteUserByLoginName(loginName);
		return new ResponseEntity<String>("User with loginName :" + loginName + " is deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
		//return new ResponseEntity<String>(loginName, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/{loginName}")
	public ResponseEntity<?> getUser(@PathVariable String loginName, HttpSession session) {
		if (session.getAttribute("loginName") != null && session.getAttribute("loginName").equals(loginName)) {
			User user = userService.findUserByLoginName(loginName);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getUsers(HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			List<User> user = userService.findAll();
			return new ResponseEntity<List<User>>(user, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
		/* |-------------------------------------------------| PRODUCT OWNER USERTYPE |--------------------------------------------------------------| */

	

	/**
	 * Method to get all tasks
	 * @return list of clients
	 */

	@GetMapping("/clients")
	public ResponseEntity<?> getAllClients(HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			List<User> clients = userService.getAllUsersByUserType("Client");
			return new ResponseEntity<List<User>>(clients, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	/**
	 * Method to authorize client to view task
	 * 
	 * @param loginName
	 * @param taskIdentifier
	 * @return client if task is authorized
	 */

	@GetMapping("/authorizeClient/{clientLoginName}/{taskIdentifier}")
	public ResponseEntity<?> addTaskToUser(@PathVariable String loginName, @PathVariable String taskIdentifier,
			HttpSession session) {
//		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

			User client = userService.addTasktoUser(loginName, taskIdentifier.toUpperCase());
			return new ResponseEntity<String>("User"+ client.getLoginName() +"authorised to view task", HttpStatus.OK);
//		}
//		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);

	}
	
	


	/* |-------------------------------------------------| DEVELOPER USERTYPE |--------------------------------------------------------------| */
	/**
	 * This method is used to update task status with for given task identifier and
	 * developer loginName
	 * 
	 * @param taskId
	 * @param developerLoginName
	 * @param task
	 * @param session
	 * @return Response Entity with updated task status if developer is logged in
	 *         else You do not have Access message is appeared with Http Status
	 */
	@PostMapping("/updatestatus/{taskId}/{developerLoginName}")
	public ResponseEntity<?> updateTaskStatus(@PathVariable String taskId, @PathVariable String developerLoginName,
			@RequestBody Task task, HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("Developer")
				&& session.getAttribute("developerLoginName").equals(developerLoginName)) {
			Task updatedTask = userService.updateTaskStatus(taskId, developerLoginName, task);
			return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	/* |-------------------------------------------------| TEAMLEADER USERTYPE |--------------------------------------------------------------| */

	/**
	 * This method is used to create a task into the DataBase Task is created on the
	 * basis of requirements given by Product Owner
	 * 
	 * @param task
	 * @param result
	 * @param productOwnerLoginName
	 * @param teamleaderLoginName
	 * @param session
	 * @return Response Entity of new created task on basis of ProductOwner
	 *         LoginName and TeamLeader LoginName with HttpStatus
	 */
	@PostMapping("/createTask/{ownerLoginName}/{leaderLoginName}")
	public ResponseEntity<?> createNewTask(@Valid @RequestBody Task task, BindingResult result,
			@PathVariable String ownerLoginName, @PathVariable String leaderLoginName, HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			ResponseEntity<?> errorMap = errorService.mapValidationError(result);
			if (errorMap != null)
				return errorMap;
			Task savedTask = userService.createTask(task, ownerLoginName, leaderLoginName);
			return new ResponseEntity<Task>(savedTask, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Method to get list of all tasks
	 * 
	 * @return list of all task list
	 */
	@GetMapping("/tasks")
	public ResponseEntity<?> getTasks(HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			Set<Task> tasks=new HashSet<>();
			if((tasks= userService.getAllTasks(session)) != null){
			return new ResponseEntity<Set<Task>>(tasks, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Tasks not found.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}


	/**
	 * Method to find the Task by taskIdentifier
	 * 
	 * @param taskIdentifier
	 * @return task if found
	 */
	@GetMapping("/task/{taskIdentifier}")
	public ResponseEntity<?> getTaskByTaskIdentifier(@PathVariable String taskIdentifier, HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			Task task;
			if((task= userService.getTaskByTaskIdentifier(taskIdentifier.toUpperCase(), session))!=null) {
				return new ResponseEntity<Task>(task, HttpStatus.OK);
			}
			return new ResponseEntity<String>("Task with id: "+taskIdentifier+" not found.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * This method is used to delete task based on Task identifier
	 * 
	 * @param taskID
	 * @param session
	 * @return Response Entity with Deleted Task given by task identifier Team
	 *         Leader is logged in else You do not have Access message is appeared
	 *         with HttpStatus
	 */
	@DeleteMapping("/task/{taskIdentifier}")
	public ResponseEntity<?> deleteTask(@PathVariable String taskIdentifier, HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			userService.deleteTask(taskIdentifier);
			return new ResponseEntity<String>("Task with Identifier " + taskIdentifier.toUpperCase() + " deleted successfully",
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * This method is used to update task if teamleader is loged in
	 * @param task
	 * @param result
	 * @param session
	 * @return updated task if executed successfully
	 */
	@PatchMapping("/task/update/{taskIdentifier}")
	public ResponseEntity<?> updateTask(@Valid @RequestBody Task task, BindingResult result,
			HttpSession session) {
		if (session.getAttribute("loginName") != null) {
			ResponseEntity<?> errorMap = errorService.mapValidationError(result);
			if (errorMap != null)
				return errorMap;
			Task updatedTask = userService.updateTask(task);
			return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * This method is method is used to assign task to developer
	 * 
	 * @param taskIdentifier
	 * @param devLoginName
	 * @param session
	 * @return Resposne Entity with Task assigned to developer if Team Leader is
	 *         logged in else You do not have Access message is appeared with
	 *         HttpStatus
	 */
	@PatchMapping("/assignDeveloper/{taskIdentifier}/{devLoginName}")
	public ResponseEntity<?> assignDeveloperToTask(@PathVariable String taskIdentifier,
			@PathVariable String devLoginName, HttpSession session) {
		if (session.getAttribute("userType") != null) {
			Task savedTask = userService.assignDeveloper(taskIdentifier, devLoginName);
			return new ResponseEntity<Task>(savedTask, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	/* |-------------------------------------------------| CLIENT USERTYPE |--------------------------------------------------------------| */
	/**
	 * This controller is used for calling add remark method from client service.
	 * Will also be used for retrieving all the errors from input remark object.
	 * @param remark is the object of Remark to be saved
	 * @param task_id is the unique task identifier.
	 * @return saved remark if no errors found or map of the errors found in the input remark object.
	 */	
	@PostMapping("/addremark/{task_id}")
		public ResponseEntity<?> addRemark(@Valid @RequestBody Remark remark, BindingResult bindingResult,@PathVariable String taskIdentifier, HttpSession session){
			if (session.getAttribute("loginName") != null && session.getAttribute("loginName").equals(remark.getGivenBy())) {	
				ResponseEntity<?> errorMap = errorService.mapValidationError(bindingResult);
				if(errorMap!=null) return errorMap;
				Task task = userService.addRemark(remark,taskIdentifier);
				return new ResponseEntity<Task>(task,HttpStatus.OK);
			}
			return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
		}
	/* |-------------------------------------------------| END |--------------------------------------------------------------| */




	

}
