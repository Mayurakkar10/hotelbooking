package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;
@Service
public class UsersService {
	@Autowired
UsersRepository repo;
	public boolean isAddNewUser(UsersModel model) {
		return repo.isAddNewUser(model);
		
	}

}
