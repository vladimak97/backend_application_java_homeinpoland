package com.service1.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//generate bean definitions and service requests for those beans at runtime

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //final -  only associates (class) with the class itself
    private final CustomAuthProvider customAuthProvider;
    private final UserDetailsService userDetailsService;

    //The @Lazy annotation makes the CustomAuthProvider component
    //injected when it is needed, thus avoiding
    //circular dependency injection.

    SecurityConfig(@Lazy CustomAuthProvider customAuthProvider,
                   UserDetailsService userDetailsService

    ){//CustomAuthProvider){

        super(); //constructor of the class from which we inherit: WebSecurityConfigurerAdapter
        this.customAuthProvider = customAuthProvider;
        this.userDetailsService = userDetailsService;

    }
    //this method creates a 'configuration for our authentication manager'.
    //and we tell it to let the authentication manager use it to load the users
    //when logging in based on their logins using the UserDetailsService
    //method -> auth.authenticationProvider(this.customAuthProvider); indicates
    //the class object to be used when checking the user's password

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
        auth.authenticationProvider(this.customAuthProvider);
    }

    // indicates that the method returned by the function is an instance of the selected class

    // and this instance is to be managed by spring so we can inject this instances into other components

    @Bean

    // PasswordEncoder is an interface that is implemented by the class
    // BCryptPasswordEncoder which implements the encode method from the PasswordEncoder interface
    // and additionally has a matches method which allows us to compare the password
    // in plaintext with the password that is hashed in the dnaych database.

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    // .httpBasic() adds the Basic Auth login capability
    // using this method we will tell spring which endpoints
    // require user data and which ones anyone can access

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/registration","/signin","/home","/checklogin")
                .permitAll()
                .antMatchers("/deleteUser","/deleteAccount").authenticated()
                .and()
                .httpBasic().and()
                .csrf().disable();
    }

    // headerName Authorization
    // header value Basic base64("user1:password1")
    // header value Basi nskduicowSdknscd
    // register the AuthenticationManager bean to enable its injection
    // into the UserService to be able to authenticate the user

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        //Keyword super indicates that we are calling the method of the authenticationManagerBean method
        //from the WebSecurityConfigurerAdapter class from which we inherit the

        return super.authenticationManagerBean();
    }
}
