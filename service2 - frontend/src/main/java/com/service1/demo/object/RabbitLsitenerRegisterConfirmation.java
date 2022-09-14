package com.service1.demo.object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;


@Controller
public class RabbitLsitenerRegisterConfirmation {

    private final ObjectMapper mapper = new ObjectMapper();
    private MailingService mailingService;

    public RabbitLsitenerRegisterConfirmation(MailingService mailingService){
        this.mailingService = mailingService;
    }

    @RabbitListener(queues = {"registrationStatus"})
    public void sendConfirmationLink(String result){
        try{
            JsonNode read = this.mapper.readValue(result, JsonNode.class);

            boolean registrationResult = read.get("success").booleanValue();
            if(registrationResult){
                String userEmail = read.get("email").textValue();
                this.mailingService.sendMail(userEmail, "Registration confirm", "Please confirm your password");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
