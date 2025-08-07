package ru.rabtra.baranoffShop.model;

import jakarta.persistence.*;
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
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String address;

    @Column(name = "phone_number")
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
