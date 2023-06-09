package com.socialconnect;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.socialconnect.model.Profile;
import com.socialconnect.model.Publication;
import com.socialconnect.model.User;
import com.socialconnect.services.PublicationsService;
import com.socialconnect.services.UserService;

@SpringBootApplication
public class SocialConnectApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PublicationsService publicationsService;

	public static void main(String[] args) {
		SpringApplication.run(SocialConnectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		User user = new User();
		user.setEmail("cesar@gmail.com");
		user.setLastName("Bonilla");
		user.setName("Cesar");
		user.setPassword("root");
		Profile profile = new Profile();
		profile.setNickName("Bonicesar");
		profile.setTelefono("1111111");
		profile.setProfile("foto.png");
		profile.setUser(user);
		user.setProfile(profile);
		
		User userSaved = userService.saveUser(user, profile);
		System.out.println("Usuario guardado: "+userSaved.getEmail());
		
		Publication publication = new Publication();
		publication.setBody("HOLA, ESTA ES MI PRIMER PUBLICACIÓN");
		publication.setDate(new Date());
		publication.setUser(userSaved);
		//Publication publicationSaved = publicationsService.savePublication(publication);
		//System.out.println("Publicación guardada: "+publicationSaved.getId()+ " por User: "+publication.getUser().getProfile().getNickName());
	}

}
