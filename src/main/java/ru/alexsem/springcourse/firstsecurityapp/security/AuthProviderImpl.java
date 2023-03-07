package ru.alexsem.springcourse.firstsecurityapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.alexsem.springcourse.firstsecurityapp.services.PersonDetailsService;

import java.util.Collections;
@Component
public class AuthProviderImpl implements AuthenticationProvider {
    
    private final PersonDetailsService personDetailsService;
    
    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    
    /**
     * Метод с первого урока.
     * Здесь лежит логика аутентификации пользователя. Вызывается Spring.
     * Аутентификация в данном случае осуществляется через запрос к БД
     *
     * На вход подаём объект Authentication с данными Credentials (login, пароль).
     *
     * Возвращает объект Authentication с принципалом, паролем и
     * списком прав (авторизаций): new UsernamePasswordAuthenticationToken(
     * personDetails, password, Collections.emptyList()), Данный объект будет
     * помещён в сессию и при каждом запросе пользователя он будет
     * доставаться из сессии.
     *
     * Каждый раз, когда пользователь будет делать запрос к нашему приложению, мы будем
     * иметь доступ к его объекту Authentication с Principal внутри. Объект Authentication
     * помещается в сессию. За загрузку объекта при каждм запросе http отвечает фильтр Spring Security.
     *
     * У сессии на сервере есть ID, который сравнивается с id в cookies
     *
     * @param authentication credentials (login, пароль)
     * @return объект Authentication с принципалом, паролем и списком прав (авторизаций)
     * @throws AuthenticationException
     */
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        Извлекаем имя из формы:
        String username = authentication.getName();
//        Ищем сущность в таблице бд по имени. Если не находим, то метод throws exception:
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
//        Извлекаем пароль из формы:
        String password = authentication.getCredentials().toString();
//        Должны сравнить пароль c формы с тем, что есть в БД в personDetails (у нашего пользователя):
        if (!password.equals(personDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");
//Возвращает объект Authentication с принципалом, паролем и списком прав (авторизаций):
        return new UsernamePasswordAuthenticationToken(personDetails, password,
                Collections.emptyList());
    }
    
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}