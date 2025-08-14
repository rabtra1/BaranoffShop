package ru.rabtra.baranoffShop.controller;

import jakarta.persistence.PostRemove;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rabtra.baranoffShop.dto.*;
import ru.rabtra.baranoffShop.model.User;
import ru.rabtra.baranoffShop.security.CustomUserDetails;
import ru.rabtra.baranoffShop.service.CategoryService;
import ru.rabtra.baranoffShop.service.CustomUserDetailsService;
import ru.rabtra.baranoffShop.service.OrderService;
import ru.rabtra.baranoffShop.service.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final CustomUserDetailsService  customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    public ProfileController(UserService userService, CategoryService categoryService, OrderService orderService, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
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

        if (!model.containsAttribute("nameUpdate")) {
            model.addAttribute("nameUpdate", new NameUpdateDTO());
        }

        if (!model.containsAttribute("emailUpdate")) {
            model.addAttribute("emailUpdate", new EmailUpdateDTO());
        }

        if (!model.containsAttribute("phoneUpdate")) {
            model.addAttribute("phoneUpdate", new PhoneUpdateDTO());
        }

        if (!model.containsAttribute("addressUpdate")) {
            model.addAttribute("addressUpdate", new AddressUpdateDTO());
        }

        if (!model.containsAttribute("passwordUpdate")) {
            model.addAttribute("passwordUpdate", new PasswordUpdateDTO());
        }

        return "profile-page";
    }

    @PostMapping("/update-name")
    public String updateName(
            @Valid @ModelAttribute("nameUpdate") NameUpdateDTO  nameUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.nameUpdate", bindingResult);
            redirectAttributes.addFlashAttribute("nameUpdate", nameUpdateDTO);
            return "redirect:/profile#info";
        }

        var user = getUser(principal);
        user.setFirstName(nameUpdateDTO.getFirstName());
        user.setLastName(nameUpdateDTO.getLastName());

        userService.update(user.getId(), user);

        return "redirect:/profile";
    }

    @PostMapping("/update-email")
    public String updateEmail(
            @Valid @ModelAttribute("emailUpdate") EmailUpdateDTO emailUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.emailUpdate", bindingResult);
            redirectAttributes.addFlashAttribute("emailUpdate", emailUpdateDTO);
            return "redirect:/profile#info";
        }

        var user = getUser(principal);

        if (user.getEmailVerified()) {
            var token = UUID.randomUUID().toString();
            var tokenExpiration = LocalDateTime.now().plusMinutes(30);
            user.setEmailVerified(false);
            user.setVerificationToken(token);
            user.setTokenExpiration(tokenExpiration);
            user.setEmail(emailUpdateDTO.getEmail());
            userService.update(user.getId(),user);
        } else {
            System.out.println("INFO: updateEmail some problem");
        }

        var userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getEmail());
        var newAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profile";

    }

    @PostMapping("/update-phone")
    public String updatePhone(
            @Valid @ModelAttribute("phoneUpdate") PhoneUpdateDTO phoneUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.phoneUpdate", bindingResult);
            redirectAttributes.addFlashAttribute("phoneUpdate", phoneUpdateDTO);
            return "redirect:/profile#info";
        }

        var user =  getUser(principal);
        user.setPhone(phoneUpdateDTO.getPhone());
        userService.update(user.getId(),user);

        return "redirect:/profile";
    }

    @PostMapping("/update-address")
    public String updateAddress(
            @Valid @ModelAttribute("addressUpdate") AddressUpdateDTO addressUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addressUpdate", bindingResult);
            redirectAttributes.addFlashAttribute("addressUpdate", addressUpdateDTO);
            return "redirect:/profile#info";
        }

        var user = getUser(principal);
        user.setAddress(addressUpdateDTO.getAddress());
        userService.update(user.getId(),user);

        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @Valid @ModelAttribute("passwordUpdate") PasswordUpdateDTO passwordUpdateDTO,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordUpdate", bindingResult);
            redirectAttributes.addFlashAttribute("passwordUpdate", passwordUpdateDTO);
            return "redirect:/profile";
        }

        var user = getUser(principal);

        String newPassword = passwordUpdateDTO.getNewPassword();
        String confirmPassword = passwordUpdateDTO.getConfirmNewPassword();
        String currentPassword = passwordUpdateDTO.getCurrentPassword();

        boolean passwordsAreEquals = newPassword.equals(confirmPassword);
        boolean oldPasswordMatch = passwordEncoder.matches(currentPassword, user.getPassword());

        if (passwordsAreEquals && oldPasswordMatch) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.update(user.getId(), user);
        }

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
            System.out.println("INFO: Verified");
            return "redirect:/profile";
        } else {
            return "error/email-verification-failed";
        }
    }

}