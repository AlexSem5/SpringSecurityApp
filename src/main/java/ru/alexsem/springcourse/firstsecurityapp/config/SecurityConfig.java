package ru.alexsem.springcourse.firstsecurityapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.alexsem.springcourse.firstsecurityapp.security.AuthProviderImpl;

/**
 * Главный класс по настройке Spring Security.
 * Здесь настраиваем авторизацию и аутентификацию
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final AuthProviderImpl authProvider;
    
    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }
    
    //настраивает аутентификацию:
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}