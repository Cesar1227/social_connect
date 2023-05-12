package com.socialconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialconnect.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByname(String username);
	public User findByemail(String email);
	public List<User> findAll();
}
