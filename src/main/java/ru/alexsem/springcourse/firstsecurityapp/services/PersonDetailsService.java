package ru.alexsem.springcourse.firstsecurityapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;
import ru.alexsem.springcourse.firstsecurityapp.repositories.PeopleRepository;
import ru.alexsem.springcourse.firstsecurityapp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    
    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    
    
    /**
     * Ищем сущность в таблице БД по имени из формы.
     * Возвращаем класс-обёртку над сущностью Person.
     * Метод вызывается в методе authentication() класса AuthProviderImpl
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
//        Извлекаем из Optional person и кладём в класс-обёртку над сущностью Person:
        return new PersonDetails(person.get());
    }
}
