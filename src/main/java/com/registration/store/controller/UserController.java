package com.registration.store.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.store.entity.User;
import com.registration.store.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}

	@PostConstruct
	public void initRolesAndUser() {
		 userService.initRolesAndUser();
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "this is for user";
		
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")  // this used for specific role jwtTOken as if Token is for Admin then only Admin path validate e
	public String forAdmin() {
		return "this is for admin";
		
	}
}
