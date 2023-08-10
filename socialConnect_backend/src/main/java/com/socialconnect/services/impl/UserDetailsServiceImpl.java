package com.socialconnect.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socialconnect.model.User;
import com.socialconnect.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Iniciando en loadUserByUsername (email) en UserDetailsServiceImpl del usuario: "+email);
		
		User user = this.userRepository.findByemail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}else {
			System.out.println("EL USUARIO HA SIDO ENCONTRADO ------------------------------");
		}
		return user;
	}
	
	
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		System.out.println("Iniciando en loadUserByUsername (email) en UserDetailsServiceImpl");
		User user = this.userRepository.findByemail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}else {
			System.out.println("EL USUARIO HA SIDO ENCONTRADO ------------------------------");
		}
		return user;
	}

}
