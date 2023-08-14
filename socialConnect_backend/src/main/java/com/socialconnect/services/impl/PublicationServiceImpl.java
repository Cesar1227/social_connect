package com.socialconnect.services.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.socialconnect.model.Profile;
import com.socialconnect.model.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialconnect.model.Publication;
import com.socialconnect.model.User;
import com.socialconnect.repository.PublicationsRepository;
import com.socialconnect.repository.UserRepository;
import com.socialconnect.services.PublicationsService;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PublicationServiceImpl implements PublicationsService {
	
	@Autowired
	private PublicationsRepository publicationsRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private S3Service s3Service;

	public PublicationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Publication savePublication(Publication publication) throws Exception {
		if(publication.getUser().getEmail()==null || publication.getUser().getId()==null) {
			System.err.println("La publicación no tiene un user_email ni user_id");
			return publication;
		}else {
			if(publication.getUser().getEmail()!=null) {
				User user = userRepository.findByemail(publication.getUser().getEmail());
				publication.setUser(user);
			}else {
				Optional<User> user = userRepository.findById(publication.getUser().getId());
				publication.setUser(user.get());
			}
			if(publication.getPicture()!=null && publication.getPicture().isFile()){
				publication.setKeyPicture(s3Service.putObject((MultipartFile) publication.getPicture()));
				publication.setUrlPicture(s3Service.getObjectUrl(publication.getKeyPicture()));
			}

			Publication publicationLocal = publicationsRepository.save(publication);
			return publicationLocal;
		}
	}

	@Override
	public Publication getPublication(Long id) {
		Publication publication = publicationsRepository.findByid(id);
		publication.setUrlPicture(s3Service.getObjectUrl(publication.getKeyPicture()));
		publication.setUser(publicationsRepository.findByid(id).getUser());
		//System.out.println(publication.getUser().getEmail());
		return publication;
	}

	@Override
	public List<Publication> getPublications(User user) {
		List<Publication> publications = publicationsRepository.findAllByuser(user);
		for(Publication p :publications){
			if(p.getKeyPicture()!=null)
				p.setUrlPicture(s3Service.getObjectUrl(p.getKeyPicture()));
		}
		return publications;
	}

	@Override
	public void deletePublication(long id) {
		publicationsRepository.deleteById(id);
		Publication publicationLocal = publicationsRepository.findByid(id);
		s3Service.deleteObject(publicationLocal.getKeyPicture());
	}

	@Override
	public List<Publication> getDashboardPublications(User user) {
		
		return null;
	}

}
