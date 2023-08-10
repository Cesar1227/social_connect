package com.socialconnect.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Profile;
import com.socialconnect.model.User;
import com.socialconnect.repository.ProfileRepository;
import com.socialconnect.repository.UserRepository;
import com.socialconnect.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public User saveUser(User user, Profile profile) throws Exception {
		User userLocal = userRepo.findByemail(user.getEmail());
		
		if(userLocal != null) {
			System.out.println("El usuario ya existe");
		}else {
			userLocal = userRepo.save(user);
			Profile profileLocal = profileRepo.save(profile);
			userLocal.setProfile(profileLocal);
		}
		return userLocal;
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByemail(email);
	}

	@Override
	public List<User> getUsers() {
		List<User> usuarios = userRepo.findAll();
		return usuarios;
	}

	@Override
	public void deleteUser(long id) {
		userRepo.deleteById(id);
	}

	/*@Override
	public User getUserByNickName(String nickName) {
		return userRepo.findBynickName(nickName);
	}*/

}
