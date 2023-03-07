package ru.alexsem.springcourse.firstsecurityapp.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;
import ru.alexsem.springcourse.firstsecurityapp.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {
    // для каких классов используется
    
    private final PersonDetailsService personDetailsService;
    
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }
    
    // Если выбросилось исключение, значит человека нет в базе
//    Лучше реализовать PeopleService и не опираться на исключение
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}