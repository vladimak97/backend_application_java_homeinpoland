package com.service1.demo.object;

import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailingService {
    private JavaMailSenderImpl mailSender;

    public MailingService(JavaMailSenderImpl mailSender){
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String title, String content){

        MimeMessagePreparator preparator = mimeMessage -> {
            MailMessage message = new SimpleMailMessage();
            message.setFrom("lovelytraveller.blog@gmail.com");
            message.setTo(to);
            message.setSubject(title);
            message.setText(content);
        };

        this.mailSender.send(preparator);
    }
}

