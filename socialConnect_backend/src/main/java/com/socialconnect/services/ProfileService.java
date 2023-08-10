package com.socialconnect.services;

import com.socialconnect.model.Profile;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
	public Profile getUserByNickName(String nickName);
	public Profile addFollow(String current_user, String nickName);
	public Profile stopFollowing(String current_user, String nickName);

	public Profile setPhoto(MultipartFile file, String nickName);

	String uploadPhoto(MultipartFile file);
	boolean deletePhoto(String key, String nickName);

}
