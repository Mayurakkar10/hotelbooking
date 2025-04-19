package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.UsersModel;
import com.example.demo.services.UsersService;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    @Autowired
    UsersService service;

    @PostMapping("/addUsers")
    public String register(@RequestBody UsersModel user) {
        boolean added = service.isAddNewUser(user);
        return added ? "User Registered Successfully" : "Registration Failed";
    }

    @GetMapping("/getallusers")
    public List<UsersModel> getAllUsers(){
    	return service.getAllUsers();
    }
    @PostMapping("/loginUser")
    public String login(@RequestBody UsersModel user) {
        String role = service.login(user);
        return role != null ? role : "Login Failed Invalid Credentials";
    }
}
