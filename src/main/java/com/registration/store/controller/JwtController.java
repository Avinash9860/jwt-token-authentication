package com.registration.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.registration.store.entity.JwtRequest;
import com.registration.store.entity.JwtResponse;
import com.registration.store.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	JwtService jwtService;
	
	
	@PostMapping({"/authenticate"})
	public JwtResponse creatJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		return jwtService.createjwtToken(jwtRequest);
		
		
		
	}
	

}
