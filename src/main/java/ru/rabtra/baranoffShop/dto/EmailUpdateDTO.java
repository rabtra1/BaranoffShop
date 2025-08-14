package ru.rabtra.baranoffShop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailUpdateDTO {
    @Email(message = "Вы ввели неверный email")
    @NotBlank(message = "Поле ввода не может быть пустым")
    private String email;
}
