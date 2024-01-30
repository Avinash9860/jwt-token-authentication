package com.registration.store.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.registration.store.dao.UserDao;
import com.registration.store.entity.JwtRequest;
import com.registration.store.entity.JwtResponse;
import com.registration.store.entity.User;
import com.registration.store.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService{
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	public JwtResponse createjwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		String newGeneratedToken = jwtutil.generateToken(userDetails);
		User user = userDao.findById(userName).get() ;
		
		return new JwtResponse(user, newGeneratedToken);
		
		
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDao.findById(userName).get();
		if(user!=null ) {
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),user.getUserPassword(),
					getAuthorities(user));
		}else {
			throw new UsernameNotFoundException("user Name is Not Valid!!");
		}
	}
	
	
	private Set getAuthorities(User user ) {
		Set authorities = new HashSet();
		
		
		user.getRole().forEach(role->{
			authorities.add(
					new SimpleGrantedAuthority("ROLE_"+role.getRoleName())
					);
		});
		return authorities;
	}
	
	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));

			
		} catch (DisabledException e) {
			throw new Exception("User is Disabled");
			// TODO: handle exception
		}catch (BadCredentialsException e) {
			
			throw new Exception("Bad Crediantial From user");
			// TODO: handle exception
		}
		
	}

}
