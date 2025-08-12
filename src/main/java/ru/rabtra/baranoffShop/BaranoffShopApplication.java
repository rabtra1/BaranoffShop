package ru.rabtra.baranoffShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BaranoffShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaranoffShopApplication.class, args);
    }

}
