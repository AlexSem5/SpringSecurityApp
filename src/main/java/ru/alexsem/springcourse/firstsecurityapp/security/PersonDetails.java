package ru.alexsem.springcourse.firstsecurityapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alexsem.springcourse.firstsecurityapp.models.Person;

import java.util.Collection;

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
        return null;
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
