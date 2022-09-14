package com.service1.demo.services;

import com.service1.demo.entities.User;
import com.service1.demo.entities.UserDetailsImpl;
import com.service1.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  this.userRepository.findByUsername(username);
        if(user != null){
            return new UserDetailsImpl(user);
        }else{
            throw new UsernameNotFoundException("User with login: " + username + " does not exists");
        }
    }
}
