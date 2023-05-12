package com.socialconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialconnect.model.Publication;
import com.socialconnect.model.User;

public interface PublicationsRepository extends JpaRepository<Publication, Long>{
	public Publication findByid(Long id);
	public List<Publication> findAllByuser(User user);
}
