package com.socialconnect.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Profile;
import com.socialconnect.repository.ProfileRepository;
import com.socialconnect.repository.UserRepository;
import com.socialconnect.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;

	public ProfileServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Profile getUserByNickName(String nickName) {
		Profile profile = profileRepository.findBynickName(nickName);
		return profile;
	}

	@Override
	public Profile addFollow(String current_user, String nickNameToFollow) {
		if(profileRepository.existsByNickName(nickNameToFollow)) {
			Profile profile = this.getUserByNickName(current_user);
			if(profile!=null) {
				List<String> follows = profile.getFollows();
				if(follows==null) {
					follows= new ArrayList<>();
				}
				follows.add(nickNameToFollow);
				profile.setFollows(follows);
				this.profileRepository.save(profile);
				return profile;
			}
		}
		return null;
	}
	
	@Override
	public Profile stopFollowing(String current_user, String nickNameToStopfollow) {
		if(profileRepository.existsByNickName(nickNameToStopfollow)) {
			Profile profile = this.getUserByNickName(current_user);
			if(profile!=null) {
				List<String> follows = profile.getFollows();
				if(follows==null) {
					return null;
				}
				
				boolean removed = follows.remove(nickNameToStopfollow);
				//follows.add(nickNameToFollow);
				if(removed) {
					profile.setFollows(follows);
					this.profileRepository.save(profile);
					return profile;
				}else {
					return null;
				}
			}
		}
		return null;
	}

}
