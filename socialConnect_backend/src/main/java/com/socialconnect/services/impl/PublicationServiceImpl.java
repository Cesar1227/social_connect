package com.socialconnect.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Publication;
import com.socialconnect.model.User;
import com.socialconnect.repository.PublicationsRepository;
import com.socialconnect.repository.UserRepository;
import com.socialconnect.services.PublicationsService;

@Service
public class PublicationServiceImpl implements PublicationsService {
	
	@Autowired
	private PublicationsRepository publicationsRepository;
	
	@Autowired
	private UserRepository userRepository;

	public PublicationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Publication savePublication(Publication publication) throws Exception {
		if(publication.getUser().getEmail()==null || publication.getUser().getId()==null) {
			System.out.println("La publicación no tiene un user_email ni user_id");
			return publication;
		}else {
			if(publication.getUser().getEmail()!=null) {
				User user = userRepository.findByemail(publication.getUser().getEmail());
				publication.setUser(user);
			}else {
				Optional<User> user = userRepository.findById(publication.getUser().getId());
				publication.setUser(user.get());
			}
			Publication publicationLocal = publicationsRepository.save(publication);
			return publicationLocal;
		}
	}

	@Override
	public Publication getPublication(Long id) {
		Publication publication = publicationsRepository.findByid(id);
		publication.setUser(publicationsRepository.findByid(id).getUser());
		//System.out.println(publication.getUser().getEmail());
		return publication;
	}

	@Override
	public List<Publication> getPublications(User user) {
		List<Publication> publicaciones = publicationsRepository.findAllByuser(user);
		return publicaciones;
	}

	@Override
	public void deletePublication(long id) {
		publicationsRepository.deleteById(id);
		
	}

}
