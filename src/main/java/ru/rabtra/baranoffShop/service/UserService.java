package ru.rabtra.baranoffShop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabtra.baranoffShop.model.User;
import ru.rabtra.baranoffShop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void save(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setVerificationToken(token);
        user.setTokenExpiration(expiryDate);
        user.setEmailVerified(false);

        System.out.println("Before save:");
        System.out.println("Email Verified: " + user.getEmailVerified());
        System.out.println("Verification Token: " + user.getVerificationToken());
        System.out.println("Token Expiration: " + user.getTokenExpiration());

        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateFirstNameAndLastName(Long id, String firstName, String lastName) {
        userRepository.updateFirstAndLastNameByUserId(firstName, lastName, id);
    }

    public void sendVerificationLink(User user) {
        emailService.sendVerificationMail(user.getEmail(), user.getVerificationToken());
    }

    @Transactional
    public Boolean verifyUser(String token) {
        var user =  userRepository.findByVerificationToken(token)
                .orElseThrow();
        System.out.println(user);
        if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            return false;
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);

        return true;
    }
}
