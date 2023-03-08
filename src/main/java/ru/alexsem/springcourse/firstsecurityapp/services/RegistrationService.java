package ru.alexsem.springcourse.firstsecurityapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;
import ru.alexsem.springcourse.firstsecurityapp.repositories.PeopleRepository;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    
    //    Bean - объект, который будет заниматься шифрованием паролей.
    /**
     * Bean - объект, который будет заниматься шифрованием паролей.
     * Задекларирован в классе SecurityConfig.
     * BCryptPasswordEncoder() - используем алгоритм bcrypt
     */
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public void register(Person person) {
//        У человека будет храниться зашифрованный пароль:
        person.setPassword(passwordEncoder.encode(person.getPassword()));
//        person.setRole("ROLE_USER");
//        Сохраняем человека в JPA репозитории
        peopleRepository.save(person);
    }
}