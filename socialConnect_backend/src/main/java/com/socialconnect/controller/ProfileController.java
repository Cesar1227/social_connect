package com.socialconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialconnect.model.Profile;
import com.socialconnect.responses.GenericResponse;
import com.socialconnect.responses.constants.ProfileConstants;
import com.socialconnect.services.ProfileService;
import com.socialconnect.services.UserService;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController<T> {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/followUser")
	public ResponseEntity addFollow(@RequestParam("current_user") String current_user, @RequestParam("nickNameToFollow") String nickNameToFollow) {
		Profile profile = profileService.addFollow(current_user, nickNameToFollow);
		if(profile!=null) {
			return ResponseEntity.ok(new GenericResponse<Profile>(profile, ProfileConstants.PROFILE_UPDATE));
		}else {
			return new ResponseEntity<GenericResponse>(new GenericResponse<Profile>(null, ProfileConstants.PROFILE_NOT_UPDATE),
					HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
			
		}
		
	}
	
	@PostMapping("/stopFollowUser")
	public ResponseEntity StopFollowing(@RequestParam("current_user") String current_user, @RequestParam("nickNameToStopfollow") String nickNameToStopfollow) {
		Profile profile = profileService.stopFollowing(current_user, nickNameToStopfollow);
		if(profile!=null) {
			return ResponseEntity.ok(new GenericResponse<Profile>(profile, ProfileConstants.PROFILE_UPDATE));
		}else {
			return new ResponseEntity<GenericResponse>(new GenericResponse<Profile>(null, ProfileConstants.PROFILE_NOT_UPDATE),
					HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
			
		}
		
	}
	
	
	public ProfileController() {
		// TODO Auto-generated constructor stub
	}

}
