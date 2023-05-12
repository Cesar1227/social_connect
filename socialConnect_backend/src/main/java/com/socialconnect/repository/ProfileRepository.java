package com.socialconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialconnect.model.Profile;
import com.socialconnect.model.Publication;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
}
