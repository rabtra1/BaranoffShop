package ru.rabtra.baranoffShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rabtra.baranoffShop.model.User;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.OrderService;
import ru.rabtra.baranoffShop.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    @Autowired
    public ProfileController(UserService userService, CategoryService categoryService, OrderService orderService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    private User getUser(Principal p) {
        return userService.findByEmail(p.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    @GetMapping()
    public String profile(
            Model model,
            Principal principal
    ){

        var user = getUser(principal);

        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("countOrdersByUserId", orderService.countByUserId(user.getId()));

        return "profile-page";
    }

    @PostMapping("/update-name")
    public String updateName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            Principal principal
    ) {

        var user = getUser(principal);
        userService.updateFirstNameAndLastName(user.getId(), firstName, lastName);

        return "redirect:/profile";
    }

    @PostMapping("/send-verification-email")
    @ResponseBody
    public ResponseEntity<String> sendVerification(
            Principal principal
    ) {
        var user = getUser(principal);
        userService.sendVerificationLink(user);
        return ResponseEntity.ok("Verification link has been sent");
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            System.out.println(token);
            return "redirect:/profile";
        } else {
            return "error/email-verification-failed";
        }
    }

}