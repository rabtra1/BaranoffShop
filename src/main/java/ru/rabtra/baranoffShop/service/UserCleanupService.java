package ru.rabtra.baranoffShop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.rabtra.baranoffShop.model.User;
import ru.rabtra.baranoffShop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserCleanupService {

    private final UserRepository userRepository;
    private final Logger log =  LoggerFactory.getLogger(UserCleanupService.class);

    public UserCleanupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void deleteExpiredUnverifiedUsers() {
        LocalDateTime now = LocalDateTime.now();
        var users = userRepository.findAllByEmailVerifiedFalseAndTokenExpirationBefore(now);

        if (users.isEmpty()) {
            log.info("No expired unverified users found at {}", now);
            return;
        }

        users.forEach(user -> {
           log.info("Deleting expired unverified user: id={}, email={}, tokenExpiration={}",
                   user.getId(), user.getEmail(), user.getTokenExpiration());
        });

        userRepository.deleteAll(users);

        log.info("Deleted {} expired unverified users at {}", users.size(), now);
    }

}