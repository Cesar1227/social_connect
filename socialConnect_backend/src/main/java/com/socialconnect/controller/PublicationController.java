package com.socialconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialconnect.model.Publication;
import com.socialconnect.model.User;
import com.socialconnect.services.PublicationsService;
import com.socialconnect.services.UserService;

@RestController
@RequestMapping("/publications")
@CrossOrigin("*")
public class PublicationController {
	
	@Autowired
	private PublicationsService publicationsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public Publication savePublication(@RequestBody Publication publication) throws Exception{
		if(publication.getUser()!=null) {
			if(publication.getUser().getEmail()!=null) {
				User user = userService.getUser(publication.getUser().getEmail());
				publication.setUser(user);
			}
		}
		return publicationsService.savePublication(publication);
	}
	
	@GetMapping("/view/{publicationId}")
	public Publication getPublication(@PathVariable("publicationId") Long id) {
		Publication publication = publicationsService.getPublication(id);
		publication.setUser(publication.getUser());
		System.out.println(publication.getUser().getEmail());
		return publication;
	}
	
	@GetMapping("/{userId}")
	public List<Publication> getPublications(@PathVariable("userId") Long id) {
		User localUser = new User();
		localUser.setId(id);
		/*for(Publication p : publicaciones) {
			System.out.println(p.getBody()+" - "+p.getUser().getId());
		}*/
		return publicationsService.getPublications(localUser);
	}
	
	@DeleteMapping("/{publicationId}")
	public void deleteUser(@PathVariable("userId") Long id) {
		publicationsService.deletePublication(id);
	}

	public PublicationController() {
		// TODO Auto-generated constructor stub
	}

}
