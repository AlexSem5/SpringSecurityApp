package ru.alexsem.springcourse.firstsecurityapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alexsem.springcourse.firstsecurityapp.security.AuthProviderImpl;
import ru.alexsem.springcourse.firstsecurityapp.services.PersonDetailsService;

/**
 * Главный класс по настройке Spring Security.
 * Здесь настраиваем авторизацию и аутентификацию
 * <p>
 * Каждый раз, когда пользователь будет делать запрос к нашему приложению, мы будем
 * иметь доступ к его объекту Authentication с Principal внутри. Объект Authentication
 * помещается в сессию. За загрузку объекта при каждм http запросе отвечает фильтр Spring Security.
 * <p>
 * У сессии на сервере есть ID, который сравнивается с id в cookies
 *
 * <p>
 * Сессия - это объект на сервере, хранящий информацию о клиенте
 * Cookies - это информация в браузере клиента, которую он отправляет
 * каждый раз при обращении к серверу (пара ключ-значение)
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    Это для кастомной:
//    private final AuthProviderImpl authProvider;
//
//    @Autowired
//    public SecurityConfig(AuthProviderImpl authProvider) {
//        this.authProvider = authProvider;
//    }
//
//    //настраивает аутентификацию:
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authProvider);
//    }
    
    private final PersonDetailsService personDetailsService;
    
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        Конфигурируем сам Spring Security
//        Конфигурируем авторизацию и аутентификацию
        http
                .csrf().disable() // временно отключаем csrf токен (защиту от межсайтовой подделки запросов)
                .authorizeRequests() // начало авторизации -  предоставить разрешения для следующих url
                //.antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error")
                .permitAll() // на эти страниы проходят все
                .anyRequest().authenticated() // на все остальные страницы мы не пускаем неаутентиф. пользователей
//                .anyRequest()
//                .hasAnyRole("USER", "ADMIN")
                .and() //разделитель между блоками
                .formLogin()
                .loginPage("/auth/login") //хотим свою страницу аутентификации
                .loginProcessingUrl("/process_login") //куда отправлять данные из формы аутентификации
                .defaultSuccessUrl("/hello", true) // после успешной аутентиф. перенаправить на страницу
                .failureUrl("/auth/login?error"); //после неуспешной аутентиф. перенаправить обратно с ключом
//                .and() // добавляем процесс разлогининивания
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/auth/login"); // ссылка на разлогинивание, автопереход после него
    }
    
    //Spring настраивает аутентификацию ПО УМОЛЧАНИЮ :
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}