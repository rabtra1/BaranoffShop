package ru.rabtra.baranoffShop.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно состоять минимум из 2-х символов и максимум из 50-ти символов")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Фамилия не должна быть пустым")
    @Size(min = 2, max = 50, message = "Фамилия должна состоять минимум из 2-х символов и максимум из 50-ти символов")
    private String lastName;

    @Column
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, max=100, message = "Пароль должен содержать минимум 8 символов и максимум 100 символов")
    private String password;

    @Column
    @Email(message = "Вы ввели неверный email")
    private String email;

    @Column
    private String address;

    @Column(name = "phone_number")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Вы ввели неверный номер телефона")
    private String phone;

    @Column
    private String role;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

}
