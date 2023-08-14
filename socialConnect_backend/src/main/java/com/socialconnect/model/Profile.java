package com.socialconnect.model;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name="Profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;
	
	private String nickName;
	
	private String cellphone;
	private String keyProfile;

	@Transient
	private File profile;
	private List<String> follows;

	@Transient
	private String urlPhoto = new S3Service().getObjectUrl(this.getKeyProfile());
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Profile(){
		
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getKeyProfile() {
		return keyProfile;
	}

	public void setKeyProfile(String keyProfile) {
		this.keyProfile = keyProfile;
	}

	public File getProfile() {
		return profile;
	}

	public void setProfile(File profile) {
		this.profile = profile;
	}

	public List<String> getFollows() {
		return follows;
	}

	public void setFollows(List<String> follows) {
		this.follows = follows;
	}

	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
}
