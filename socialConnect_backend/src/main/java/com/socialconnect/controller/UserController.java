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

import com.socialconnect.model.Profile;
import com.socialconnect.model.User;
import com.socialconnect.services.ProfileService;
import com.socialconnect.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/createUser")
	public User saverUser(@RequestBody User user) throws Exception {
		if(user.getProfile()==null) {
			return null;
		}else {
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			User userCreated = userService.saveUser(user, user.getProfile());
			userCreated.setPassword(null);
			return userCreated;
		}
		
	}
	
	/*@GetMapping("/{email}")
	public User getUser(@PathVariable("email") String email) {
		User user = userService.getUser(email);
		user.setPassword(null);
		return user;
	}*/
	
	@GetMapping("/{keyFoundUser}")
	public User getUser(@PathVariable("keyFoundUser") String keyFoundUser) {
		User user = userService.getUser(keyFoundUser);
		if(user==null) {
			Profile profile= profileService.getUserByNickName(keyFoundUser);
			if(profile!=null) {
				//user = userService.getUserByNickName(keyFoundUser);
				System.out.println("[UserController] este es mi perfil: "+profile.getUser().getUsername());
				user=profile.getUser();
			}else {
				return user;
			}
		}
		user.setPassword(null);
		return user;
	}
	
	@GetMapping("/")
	public List<User> getUsers(){
		List<User> users = userService.getUsers();
		for(User p : users) {
			p.setPassword(null);
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
