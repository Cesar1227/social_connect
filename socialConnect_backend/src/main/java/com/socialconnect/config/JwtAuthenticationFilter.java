package com.socialconnect.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.socialconnect.services.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("Iniciando doFilterInternal");
		String requestTokenHeader = request.getHeader("Authorization");
		System.out.println(requestTokenHeader);
		String username = null;
		String jwtToken = null;
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7); //Descartando "Bearer "
			//jwtToken = requestTokenHeader;
			System.out.println(jwtToken);
			try {
				username = this.jwtUtils.extractUsername(jwtToken);
			}catch(ExpiredJwtException expiredJwtException) {
				System.out.println("Token has expired");
			}catch(Exception exception) {
				exception.printStackTrace();
			}
		}else {
			System.out.println("Invalid Token, This one doesn't start with Bearer string");
		}
				
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
		}else {
			System.out.println("The token is not valid");
		}
		filterChain.doFilter(request, response);
		
	}

}
