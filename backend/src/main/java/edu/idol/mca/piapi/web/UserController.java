
package edu.idol.mca.piapi.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.idol.mca.piapi.domain.User;
import edu.idol.mca.piapi.service.UserService;
import edu.idol.mca.piapi.serviceImpl.MapValidationErrorService;

/**
 *
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapValidationErrorService errorService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		User savedUser= userService.saveUser(user);

		return new ResponseEntity<User>(savedUser,HttpStatus.OK);
		//return new ResponseEntity<String>("Registration Successful",HttpStatus.OK);
	}
	
}
