package com.service1.demo.controllers;

import com.service1.demo.object.UsernameAndPasswordObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

// RestController is used to create restful web services using the @RestController annotation.

@RestController
public class UsersController {
    private AuthenticationManager manager;

    public UsersController(AuthenticationManager manager){
        this.manager = manager;
    }

    // Methods with the PostMapping annotation handle HTTP POST requests matching the specified URI expression

    @GetMapping("/home")
    public String homeMapping(){
        return "index.html";
    }

    @CrossOrigin("*")
    @PostMapping(value = "/checklogin",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody UsernameAndPasswordObject usernameAndPasswordObject,
                        HttpServletResponse response){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                usernameAndPasswordObject.getUsername(),
                usernameAndPasswordObject.getPassword()
        );
        System.out.println(usernameAndPasswordObject.getPassword());
        System.out.println(usernameAndPasswordObject.getUsername());
        if(this.manager.authenticate(token).isAuthenticated()){
            response.setStatus(HttpStatus.OK.value());
            return "{\"success\": true}";
        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "{\"success\": false}";
        }
    }
}
