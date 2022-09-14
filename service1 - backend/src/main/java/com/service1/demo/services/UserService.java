package com.service1.demo.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service1.demo.entities.User;
import com.service1.demo.object.UsernameAndPasswordObject;
import com.service1.demo.repositories.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


// The @Service is, like @Reposiotry, an alias for the @Component annotation
// which tells spring that the class and in fact its instances are managed by spring
// and thus we can use dependcy injection by injecting selected
// beans into other beans

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RabbitTemplate rabbitTemplate;

    // authentication manager is the interface that is needed to check user data during login
    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       RabbitTemplate rabbitTemplate){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void saveUser(UsernameAndPasswordObject usernameAndPasswordObject){
        User user = new User();

        // after creating a new user object
        // we assign to its username and password fields the values from the object
        // userRegistrationObject that we got from the client

        String passwordHashed = this.passwordEncoder.encode(usernameAndPasswordObject.getPassword());
        user.setUsername(usernameAndPasswordObject.getUsername());
        user.setPassword(passwordHashed);
        user.setEmail(usernameAndPasswordObject.getEmail());
        Example<User> emapleUser = Example.of(user, ExampleMatcher.matching().withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains()));
        boolean userAlreadyExists = this.userRepository.exists(emapleUser);

        // the save method saves the user object to the database and returns a new object
        // representing the saved user

        if(!userAlreadyExists){
            User u = this.userRepository.save(user);
        }
        ObjectMapper mapper = new ObjectMapper();
        try(final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            JsonGenerator generator = null;
            try{
                generator = mapper.getFactory().createGenerator(outputStream);
                generator.writeStartObject();
                generator.writeStringField("email", user.getEmail());
                generator.writeBooleanField("success", !userAlreadyExists);
                generator.writeEndObject();
                generator.flush();
                this.rabbitTemplate.convertAndSend("registrationStatus",new String(outputStream.toByteArray()));
                outputStream.close();
                generator.close();
            }catch (IOException e){
                e.printStackTrace();
            }finally{
                if(generator != null && !generator.isClosed()) {
                    try{
                        generator.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Transactional
    @Modifying
    public void deleteMyAccount(){
        String username = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        this.userRepository.deleteByUsername(username);
    }
}
