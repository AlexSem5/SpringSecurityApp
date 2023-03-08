package ru.alexsem.springcourse.firstsecurityapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;
import ru.alexsem.springcourse.firstsecurityapp.services.RegistrationService;
import ru.alexsem.springcourse.firstsecurityapp.util.PersonValidator;

import javax.validation.Valid;

// методы для аутентификации и регистрации
@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    
    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    
//    Кладём в модель пустого человека
//    Метод возвращает с сервера html-форму (Thymeleaf)
//    В форме у нас добавлен CSRF-token
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }
    
//    Получаем данные с формы
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "/auth/registration";
        }
        registrationService.register(person);
        return "redirect:/auth/login";
    }
    
}
