
package edu.idol.mca.piapi.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		if(session.getAttribute("loginName")!=null && session.getAttribute("userType").equals("ProductOwner") && session.getAttribute("loginName").equals(user.getLoginName())) {
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
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

			User user = userService.findUserByLoginName(loginName);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getUsers(HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {
			List<User> user = userService.findAll();
			return new ResponseEntity<List<User>>(user, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
		/* |-------------------------------------------------| PRODUCT OWNER USERTYPE |--------------------------------------------------------------| */

	/**
	 * Method to get list of all tasks
	 * 
	 * @return list of all task list
	 */
	@GetMapping("/tasks")
	public ResponseEntity<?> getTasks(HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {
			List<Task> tasks = userService.getAllTasks(session);
			return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
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
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

			Task task = userService.getTaskByTaskIdentifier(taskIdentifier.toUpperCase(), session);
			return new ResponseEntity<Task>(task, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Method to get all tasks
	 * @return list of clients
	 */

	@GetMapping("/clients")
	public ResponseEntity<?> getAllClients(HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

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


	/* |-------------------------------------------------| TEAMLEADER USERTYPE |--------------------------------------------------------------| */


	/* |-------------------------------------------------| CLIENT USERTYPE |--------------------------------------------------------------| */




	//-------------------------DOUPLICATE METHODs
		/**
		 * This method is used to view task assigned to client
		 * @param task_id will the unique task identifier
		 * @param loginName is the client login name 
		 * @return the Task object as a response entity
		 */
	/*	
		 @GetMapping("/viewtask/{loginName}/{task_id}")
		public ResponseEntity<?> getTaskByTaskIdentifier(@PathVariable String loginName, HttpSession session){
			
			Task task = userService.getTaskByTaskIdentifier(loginName, session);		
			return new ResponseEntity<Task>(task,HttpStatus.OK);
			
		}
	*/

		/**
		 * This method is used to view all tasks assigned to client
		 * @param loginName of the client
		 * @return list of all tasks
		 */
	/*
		@GetMapping("/viewalltask/{loginName}")
		public ResponseEntity<?> getAllTask(@PathVariable String loginName){
			
			List<Task> taskList = userService.viewAllTask(loginName);
			return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			
		}
	*/
	
	/**
	 * This controller is used for calling add remark method from client service.
	 * Will also be used for retrieving all the errors from input remark object.
	 * @param remark is the object of Remark to be saved
	 * @param task_id is the unique task identifier.
	 * @return saved remark if no errors found or map of the errors found in the input remark object.
	 */
	@PostMapping("/addremark/{task_id}")
	public ResponseEntity<?> addRemark(@Valid @RequestBody Remark remark, BindingResult bindingResult,@PathVariable String task_id){
		ResponseEntity<?> errorMap = errorService.mapValidationError(bindingResult);
		if(errorMap!=null) return errorMap;
		
		Remark addedRemark = userService.addRemark(remark,task_id);
		return new ResponseEntity<>(addedRemark,HttpStatus.OK);
		
	}

}
