package ru.alexsem.springcourse.firstsecurityapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.alexsem.springcourse.firstsecurityapp.security.AuthProviderImpl;

/**
 * Главный класс по настройке Spring Security.
 * Здесь настраиваем авторизацию и аутентификацию
 * <p>
 * Каждый раз, когда пользователь будет делать запрос к нашему приложению, мы будем
 * иметь доступ к его объекту Authentication с Principal внутри. Объект Authentication
 * помещается в сессию. За загрузку объекта при каждм http запросе отвечает фильтр Spring Security.
 *
 * У сессии на сервере есть ID, который сравнивается с id в cookies
 *
 * <p>
 * Сессия - это объект на сервере, хранящий информацию о клиенте
 * Cookies - это информация в браузере клиента, которую он отправляет
 * каждый раз при обращении к серверу (пара ключ-значение)
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