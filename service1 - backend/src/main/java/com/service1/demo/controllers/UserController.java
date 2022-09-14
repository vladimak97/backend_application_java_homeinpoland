package com.service1.demo.controllers;

import com.service1.demo.object.UsernameAndPasswordObject;
import com.service1.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

// annotation indicating that the class is a REST controller

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @CrossOrigin("*")
    @PostMapping("/registration")

    // @RequestBody indicates that the object that was sent to the server in JSON form will be mapped to a java object

    public void registration(@RequestBody UsernameAndPasswordObject usernameAndPasswordObject){
        this.userService.saveUser(usernameAndPasswordObject);
    }

    // HttpServletResponse class represents the response object from the server
    // has ready-made methods that, among other things, allow us to send headers or response status to the client

    @DeleteMapping("/deleteAccount")
    public void deleteMyAccount(){
        this.userService.deleteMyAccount();
    }
}
