package com.socialconnect.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Profile;
import com.socialconnect.repository.ProfileRepository;
import com.socialconnect.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepo;

	public ProfileServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Profile getUserByNickName(String nickName) {
		Profile profile = profileRepo.findBynickName(nickName);
		return profile;
	}

}
