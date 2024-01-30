package com.registration.store.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.registration.store.dao.RoleDao;
import com.registration.store.dao.UserDao;
import com.registration.store.entity.Role;
import com.registration.store.entity.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		
		Role role = roleDao.findById("User").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		
		return userDao.save(user);
		
	}
	
	public void initRolesAndUser() {
		
		
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin Role");
		roleDao.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default user Role for newly created Record");
		roleDao.save(userRole);
		
		User adminUser = new User();
		adminUser.setUserFirstName("Admin");
		adminUser.setUserLastName("Admin");
		adminUser.setUserName("admin123");
		
		adminUser.setUserPassword(getEncodedPassword("admin@123"));
		
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);
		
//		User user = new User();
//		user.setUserFirstName("avi");
//		user.setUserLastName("Deshmukh");
//		user.setUserName("avi123");
//		user.setUserPassword(getEncodedPassword("avi@123"));
//		
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userDao.save(user);
		
		
		
		
		
		
		
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	

}
