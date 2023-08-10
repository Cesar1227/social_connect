package com.socialconnect.services.impl;

import com.socialconnect.model.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Profile;
import com.socialconnect.repository.ProfileRepository;
import com.socialconnect.services.ProfileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private S3Service s3Service;

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

	@Override
	public Profile setPhoto(MultipartFile file, String nickName) {
		String key = this.uploadPhoto(file);
		if(key==null){
			return null;
		}
		String url = s3Service.getObjectUrl(key);
		Profile profile = profileRepository.findBynickName(nickName);
		profile.setProfile(key);
		profile.setUrlPhoto(url);
		return profileRepository.save(profile);
	}

	@Override
	public String uploadPhoto(MultipartFile file) {
		return s3Service.putObject(file);
	}

	@Override
	public boolean deletePhoto(String key, String nickName) {
		try {
			Profile profile = profileRepository.findBynickName(nickName);
			profile.setUrlPhoto("");
			profile.setProfile(null);
			profile = profileRepository.save(profile);
			s3Service.deleteObject(key);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}
