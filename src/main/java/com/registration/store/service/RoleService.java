package com.registration.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.store.dao.RoleDao;
import com.registration.store.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role);
		
	}

}
