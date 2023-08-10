package com.socialconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialconnect.model.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
	public Profile findBynickName(String nickName);
	public boolean existsByNickName(String nickName);
}
