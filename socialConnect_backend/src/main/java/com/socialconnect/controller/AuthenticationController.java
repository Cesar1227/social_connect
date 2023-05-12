package com.socialconnect.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialconnect.config.JwtUtils;
import com.socialconnect.model.JwtRequest;
import com.socialconnect.model.JwtResponse;
import com.socialconnect.model.User;
import com.socialconnect.services.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		System.out.println("Entrando a generar token");
		try {
			System.out.println("user: "+jwtRequest.getEmail()+ " pass: "+jwtRequest.getPassword());
			autenticar(jwtRequest.getEmail(),jwtRequest.getPassword());
		}catch(UsernameNotFoundException usernameNotFoundException) {
			usernameNotFoundException.printStackTrace();
			throw new Exception("Usuario no encontrado 1 user nameNotFound");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Usuario no encontrado ");
		}
		
		UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getEmail());
		String token = this.jwtUtils.generateToken(userDetails);
		System.out.println("Este es mi token: "+token);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	private void autenticar(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}catch(DisabledException disabledException){
			throw new Exception ("User disabled "+disabledException.getMessage());
		}catch(BadCredentialsException badCredentialsException) {
			throw new Exception ("Invalid credentials "+ badCredentialsException.getMessage());
		}
	}
	
	@GetMapping("/actual-usuario")
	public User obtenerUsuarioActual(Principal principal) {
		return (User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
	}
	
	public AuthenticationController() {
		// TODO Auto-generated constructor stub
	}

}
