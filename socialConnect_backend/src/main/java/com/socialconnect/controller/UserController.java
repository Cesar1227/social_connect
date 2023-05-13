package com.socialconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialconnect.model.User;
import com.socialconnect.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/createUser")
	public User saverUser(@RequestBody User user) throws Exception {
		if(user.getProfile()==null) {
			return null;
		}else {
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			User userCreated = userService.saveUser(user, user.getProfile());
			return userCreated;
		}
		
	}
	
	@GetMapping("/{email}")
	public User getUser(@PathVariable("email") String email) {
		
		return userService.getUser(email);
	}
	
	@GetMapping("/")
	public List<User> getUsers(){
		List<User> users = userService.getUsers();
		for(User p : users) {
			System.out.println(p.getUsername()+" - "+p.getName()+" - "+p.getLastName());
		}
		return users;
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long id) {
		userService.deleteUser(id);
	}

	public UserController() {
		// TODO Auto-generated constructor stub
	}

}
