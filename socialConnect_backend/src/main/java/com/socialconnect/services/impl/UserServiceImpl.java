package com.socialconnect.services.impl;

import java.util.List;

import com.socialconnect.model.S3Service;
import com.socialconnect.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Profile;
import com.socialconnect.model.User;
import com.socialconnect.repository.ProfileRepository;
import com.socialconnect.repository.UserRepository;
import com.socialconnect.services.UserService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private S3Service s3Service;
	
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
			profile.setKeyProfile(s3Service.putObject((MultipartFile) profile.getProfile()));
			profile.setUrlPhoto(s3Service.getObjectUrl(profile.getKeyProfile()));
			Profile profileLocal = profileRepo.save(profile);
			userLocal.setProfile(profileLocal);
		}
		return userLocal;
	}

	@Override
	public User updateUser(User user) {
		User userLocal = userRepo.findByemail(user.getEmail());
		if(userLocal==null){
			userLocal = userRepo.findByname(user.getEmail());
		}

		if(userLocal==null){
			return null;
		}
		//userLocal = userRepo.save(user);
		userLocal.setName(user.getName());
		userLocal.setLastName(user.getLastName());
		if(user.getPassword()!=null) {
			userLocal.setPassword(user.getPassword());
		}
		userLocal.setProfile(profileService.updateProfile(user.getProfile()));
		//userLocal.setEmail(user.getEmail());
		userLocal = userRepo.save(userLocal);
		//userLocal.getProfile().setKeyProfile(s3Service.putObject((MultipartFile) profile.getProfile()));
		userLocal.getProfile().setUrlPhoto(s3Service.getObjectUrl(userLocal.getProfile().getKeyProfile()));
		//Profile profileLocal = profileRepo.save(profile);
		//userLocal.setProfile(profileLocal);

		return userLocal;

	}


	@Override
	public User getUser(String email) {
		User userLocal = userRepo.findByemail(email);
		if (userLocal == null) {
			return null;
		}
		userLocal.getProfile().setUrlPhoto(userLocal.getProfile().getKeyProfile());
		return userLocal;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = userRepo.findAll();
		for (User user:users) {
			user.getProfile().setUrlPhoto(user.getProfile().getKeyProfile());
		}
		return users;
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
