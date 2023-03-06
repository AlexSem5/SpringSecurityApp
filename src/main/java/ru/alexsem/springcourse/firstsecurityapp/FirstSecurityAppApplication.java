package ru.alexsem.springcourse.firstsecurityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс с данной аннотацией должен быть в корне проекта
 */
@SpringBootApplication
//Содержит в себе другие аннотации, например
//@Configuration
//@ComponentScan("ru.alexsem.springcourse")
//итд
public class FirstSecurityAppApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FirstSecurityAppApplication.class, args);
    }
}
