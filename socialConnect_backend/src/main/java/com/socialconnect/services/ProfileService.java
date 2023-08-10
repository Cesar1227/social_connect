package com.socialconnect.services;

import com.socialconnect.model.Profile;

public interface ProfileService {
	public Profile getUserByNickName(String nickName);
	public Profile addFollow(String current_user, String nickName);
	public Profile stopFollowing(String current_user, String nickName);
}
