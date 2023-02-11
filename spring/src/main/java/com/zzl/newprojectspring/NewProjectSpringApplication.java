package com.zzl.newprojectspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NewProjectSpringApplication {


    public static void main(String[] args) {
        SpringApplication.run(NewProjectSpringApplication.class, args);
    }


}
