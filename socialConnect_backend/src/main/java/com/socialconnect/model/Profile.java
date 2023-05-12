package com.socialconnect.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long porfileId;
	
	private String nickName;
	
	private String telefono;
	private String profile;
	private List<User> seguidos;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Profile(){
		
	}

	public Long getPorfileId() {
		return porfileId;
	}

	public void setPorfileId(Long porfileId) {
		this.porfileId = porfileId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<User> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(List<User> seguidos) {
		this.seguidos = seguidos;
	}

	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
