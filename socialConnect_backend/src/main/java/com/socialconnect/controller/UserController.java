package com.socialconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.socialconnect.model.Profile;
import com.socialconnect.model.User;
import com.socialconnect.services.ProfileService;
import com.socialconnect.services.UserService;
import org.springframework.web.multipart.MultipartFile;

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
			user.setEnabled(true);
			User userCreated = userService.saveUser(user, user.getProfile());

			userCreated.setPassword(null);
			//System.out.println("Usuario: "+user.toString());
			//User userCreated = user;
			return userCreated;
		}
	}

	@PostMapping("/updateUser")
	public ResponseEntity updateUser(@RequestBody User user) throws Exception {
		System.out.println("Entrando a actualizar usuario");
		if(user.getProfile()==null) {
			return ResponseEntity.status(401).body(null);
		}else {
			/*if(user.getPassword()!=null && user.getPassword()!=""){
				user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			}*/
			User userUpdated = userService.updateUser(user);
			if(userUpdated==null){
				return ResponseEntity.notFound().build();
			}
			userUpdated.setPassword(null);
			return ResponseEntity.ok(userUpdated);
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
				System.out.println("[UserController] este es mi perfil: "+profile.getUser().getUsername()+" - "+profile.getUrlPhoto());
				user=profile.getUser();
			}else {
				//Aqui debe ir ResponseEntity(Not found)
				return null;
			}
		}else{
			profileService.setUrlPhoto(user.getProfile());
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
