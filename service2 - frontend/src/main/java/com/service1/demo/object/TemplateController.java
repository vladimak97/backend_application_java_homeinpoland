package com.service1.demo.object;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class TemplateController {

    private RestTemplate restTemplate;
    private final String secondServiceURL = "http://localhost:9191/";

    public TemplateController(){
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String home(){
        return "index.html";
    }

    @GetMapping("/register_page")
    public String register(){
        return "register_page.html";
    }
    @GetMapping("/personal_page")
    public String personalData(){
        return "personal_page.html";
    }

    @GetMapping("/add_apartment")
    public String updateTemplate(){
        return "update.html";
    }
    @GetMapping("/myapartments")
    public String apartment(){
        return "myapartments.html";
    }
}

