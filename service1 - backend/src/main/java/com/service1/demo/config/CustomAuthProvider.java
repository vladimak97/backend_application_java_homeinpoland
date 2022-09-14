package com.service1.demo.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//object having a graphical representation that can be displayed on the screen and that can interact with the user
@Component

//performing authentication - fully authenticated object with full credentials is returned.

public class CustomAuthProvider implements AuthenticationProvider {


    //User Details Service - access to the username in order to retrieve the full user entity (info)
    //final -  only associates (class) with the class itself
    private final UserDetailsService userDetailsService;

    //Encode the raw password.
    private final PasswordEncoder passwordEncoder;


    public CustomAuthProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    //provide a method that is already provided by parent classes
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean passwordAreEquals = this.passwordEncoder.matches(password, userDetails.getPassword());

            if(passwordAreEquals){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
                SecurityContextHolder.getContext()
                        .setAuthentication(token);

                return token;
            }
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    //representing the value true if the string argument is equal
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}


