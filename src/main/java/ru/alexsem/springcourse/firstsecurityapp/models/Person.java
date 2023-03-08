package ru.alexsem.springcourse.firstsecurityapp.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Аннотацией @Entity помечаем класс, который связан с бд.
 * Класс с @Entity должен иметь пустой конструктор и поле с аннотацией @Id
 *
 * Требования к Entity:
 *
 * Все классы должны иметь ID для простой идентификации наших объектов в БД и в Hibernate.
 * Это поле класса соединяется с первичным ключём (primary key) таблицы БД.
 * Все POJO – классы должны иметь конструктор по умолчанию (пустой).
 * Все поля  – классов должны иметь модификатор доступа private,
 * иметь набор getter-ов и setter-ов в стиле JavaBean.
 *  классы не должны содержать бизнес-логику.
 */

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;
    
    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    
    // Конструктор по умолчанию нужен для Spring
    public Person() {
    }
    
    public Person(String username, int yearOfBirth) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", yearOfBirth=" + yearOfBirth +
               ", password='" + password + '\'' +
               '}';
    }
}