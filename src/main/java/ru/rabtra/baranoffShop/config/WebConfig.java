package ru.rabtra.baranoffShop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/css/**", "/js/**", "/uploads/**")
                .addResourceLocations("classpath:/static/css/")
                .addResourceLocations("classpath:/static/js/")
                .addResourceLocations("file:uploads/");
    }
}
