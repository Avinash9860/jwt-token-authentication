package com.registration.store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.registration.store.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String>{

}
