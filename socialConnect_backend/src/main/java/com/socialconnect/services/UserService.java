package com.socialconnect.services;

import java.util.List;

import com.socialconnect.model.Profile;
import com.socialconnect.model.User;

public interface UserService {
	
	public User saveUser(User user, Profile profile) throws Exception;
	public User getUser(String email);
	public List<User> getUsers();
	public void deleteUser(long id);
}
