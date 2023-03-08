package ru.alexsem.springcourse.firstsecurityapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс-обёртка над сущностью Person, который предоставляет детали(информацию)
 * о пользователе
 */
public class PersonDetails implements UserDetails {
    private  final Person person;
    
    public PersonDetails(Person person) {
        this.person = person;
    }
    
    public Person getPerson() {
        return person;
    }
    
    //    Коллекция прав у пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Роли или Authorities (ниже только роли). В Spring Security Authorities и роли - одно и то же
//        Здесь будут находиться роли: ROLE_ADMIN или ROLE_USER
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }
    
    @Override
    public String getPassword() {
        return person.getPassword();
    }
    
    @Override
    public String getUsername() {
        return person.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
