package ru.alexsem.springcourse.firstsecurityapp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexsem.springcourse.firstsecurityapp.security.PersonDetails;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
    
    /**
     * Достаём объект (принципал - сам пользователь), который был положен в сессию после успешной
     * аутентификации в методе authentication() класса AuthProviderImpl
     *
     * Каждый раз, когда пользователь будет делать запрос к нашему приложению, мы будем
     * иметь доступ к его объекту Authentication с Principal внутри. Объект Authentication
     * помещается в сессию. За загрузку объекта при каждом http запросе отвечает фильтр Spring Security.
     *
     * У сессии на сервере есть ID, который сравнивается с id в cookies
     *
     *
     * @return
     */
    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        return "hello";
    }
    
    // пример страницы, на которую может заходить только админ
    @GetMapping ("/admin")
    public String adminPage() {
        return "admin";
    }
}
