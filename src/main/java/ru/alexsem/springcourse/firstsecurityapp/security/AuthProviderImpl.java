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
     * Метод с первого урока (возвращает credentials).
     * Здесь лежит логика аутентификации. Вызывается Spring
     * Берём даннные из объекта authentication и ищем в таблице бд
     * Возвращает объект Authentication с принципалом, паролем и
     * списком прав (авторизаций): new UsernamePasswordAuthenticationToken(
     * personDetails, password, Collections.emptyList()), Данный объект будет
     * помещён в сессию и при каждом запросе пользователя он будет
     * доставаться из сессии.
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
//        Данные из Бд:
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
//        Получаем пароль с формы:
        String password = authentication.getCredentials().toString();
//        Должны сравнить пароль c формы с тем, что есть в БД в personDetails (у нашего пользователя):
        if (!password.equals(personDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");
        return new UsernamePasswordAuthenticationToken(personDetails, password,
                Collections.emptyList());
    }
    
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}