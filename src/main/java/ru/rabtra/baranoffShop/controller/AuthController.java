package ru.rabtra.baranoffShop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rabtra.baranoffShop.model.User;
import ru.rabtra.baranoffShop.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping( "/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String processReg(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        var token = UUID.randomUUID().toString();
        var expiryDate = LocalDateTime.now().plusDays(7);
        user.setVerificationToken(token);
        user.setTokenExpiration(expiryDate);
        user.setEmailVerified(false);

        System.out.println("Before registration: ");
        System.out.println("Email Verified: " + user.getEmailVerified());
        System.out.println("Verification Token: " + user.getVerificationToken());
        System.out.println("Token Expiration: " + user.getTokenExpiration());

        userService.save(user);
        return "redirect:/auth/login";

    }

}
