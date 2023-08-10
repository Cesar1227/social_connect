package com.socialconnect.controller;

import com.socialconnect.model.Profile;
import com.socialconnect.model.S3Service;
import com.socialconnect.services.ProfileService;
import com.socialconnect.services.UserService;
import com.socialconnect.transfer.constants.ProfileConstants;
import com.socialconnect.transfer.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@CrossOrigin("*")
public class ProfileController<T> {
    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private S3Service s3Service;

    @PostMapping("/setPhoto")
    ResponseEntity setPhoto(@RequestParam("file") MultipartFile file, @RequestParam("nickName") String nickName){
        Profile profileRecived = profileService.setPhoto(file,nickName);
        if(profileRecived==null){
            return ResponseEntity
                    .status(404)
                    .body(new GenericResponse<Profile>(null,ProfileConstants.PROFILE_NOT_UPDATE));
        }
        return ResponseEntity
                .ok()
                .body(profileRecived);
    }

    @DeleteMapping("/deletePhoto")
    ResponseEntity deletePhoto(@RequestParam("key") String key, @RequestParam("nickName") String nickName){
        if(profileService.deletePhoto(key,nickName))
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body(new GenericResponse<>(null, "No se ha podido ejecutar la petición " +
                                                                        "por favor revise los parámetros e intente de nuevo"));
    }

    @PostMapping("/followUser")
    ResponseEntity addFollow(@RequestParam("current_user") String current_user, @RequestParam("nickNameToFollow") String nickNameToFollow) {
        Profile profile = profileService.addFollow(current_user, nickNameToFollow);
        if(profile!=null) {
            return ResponseEntity.ok(new GenericResponse<Profile>(profile, ProfileConstants.PROFILE_UPDATE));
        }else {
            return new ResponseEntity<GenericResponse>(new GenericResponse<Profile>(null, ProfileConstants.PROFILE_NOT_UPDATE),
                    HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }
    }

    @PostMapping("/stopFollowUser")
    ResponseEntity StopFollowing(@RequestParam("current_user") String current_user, @RequestParam("nickNameToStopfollow") String nickNameToStopfollow) {
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
