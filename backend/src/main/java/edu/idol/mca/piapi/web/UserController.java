
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	/**
	 * Method for handling User login and creating session.
	 * 
	 * @param user
	 * @param result       contains the result and error of validation
	 * @param session      Creates New Session
	 * @return 	Response Entity with logged In User with HTTP Status
	 */
	@GetMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody String loginName, String pwd, HttpSession session, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		
		
		User loggedInUser= userService.authenticateUser(loginName, pwd, session);
		//return new ResponseEntity<User>(loggedInUser,HttpStatus.OK);
		return new ResponseEntity<String>(loginName,HttpStatus.OK);
		
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
	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user,HttpSession session, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;		

		if(session.getAttribute("loginName")!=null && session.getAttribute("userType").equals("ProductOwner") && session.getAttribute("loginName").equals(user.getLoginName())) {
			User savedUser= userService.saveUser(user);
			
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
	public ResponseEntity<?> deleteProductOwner(@PathVariable String loginName, HttpSession session) {
		if (session.getAttribute("loginName") != null&& session.getAttribute("userType").equals("ProductOwner") && session.getAttribute("loginName").equals(loginName)) {

		userService.deleteUserByLoginName(loginName);
		return new ResponseEntity<String>("User with loginName :" + loginName + " is deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Method to get all the task list
	 * 
	 * @return list of all task list
	 */
	@GetMapping("/tasks")
	public ResponseEntity<?> getTaskList(HttpSession session) {
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

			List<User> clients = userService.getAllUsersByUserType("Clients");
			return new ResponseEntity<List<User>>(clients, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);

	}
	/**
	 * Method to authorize client to view task
	 * 
	 * @param clientLoginName
	 * @param taskIdentifier
	 * @return client if task is authorized
	 */

	@GetMapping("/authorizeClient/{clientLoginName}/{taskIdentifier}")
	public ResponseEntity<?> addTaskToUser(@PathVariable String loginName, @PathVariable String taskIdentifier,
			HttpSession session) {
//		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

			User client = userService.addTasktoUser(loginName, taskIdentifier.toUpperCase());
			return new ResponseEntity<String>("User authorised to view task", HttpStatus.OK);
//		}
//		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("/{loginName}")
	public ResponseEntity<?> getUser(@PathVariable String loginName, HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {

			User productOwner = userService.findUserByLoginName(loginName);
			return new ResponseEntity<User>(productOwner, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getUsers(HttpSession session) {
		if (session.getAttribute("userType") != null && session.getAttribute("userType").equals("ProductOwner")) {
			List<User> owners = userService.findAll();
			return new ResponseEntity<List<User>>(owners, HttpStatus.OK);
		}
		return new ResponseEntity<String>("You do not have Access!!!", HttpStatus.UNAUTHORIZED);
	}
	
	
	@GetMapping("/test")
	public ResponseEntity<?> test(HttpSession session) {
			
		User loggedInUser = userService.authenticateUser("admin","admin", session);
		ArrayList<String> al = new ArrayList<>();
		al.add((String) session.getAttribute("userType"));
		al.add((String) session.getAttribute("loginName"));
		
		HashMap<String, String> hm = new HashMap<>();
			hm.put("userType", (String) session.getAttribute("userType"));
			hm.put("loginName", (String) session.getAttribute("loginName"));
			return new ResponseEntity<HashMap<String, String>>(hm, HttpStatus.OK);
//			return new ResponseEntity<HttpSession>(session, HttpStatus.OK);
			

	}

}
