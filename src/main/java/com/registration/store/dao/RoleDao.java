package com.registration.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.registration.store.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
	
	

}
