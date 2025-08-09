package ru.rabtra.baranoffShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationMail(String toEmail, String token) {

        String subject = "Подтверждение регистрации";
        String verificationUrl = "http://localhost:8080/profile/verify-email?token=" + token;
        String message = "Спасибо за регистрацию! Перейдите по ссылке, чтобы подтвердить email:\n" + verificationUrl;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
