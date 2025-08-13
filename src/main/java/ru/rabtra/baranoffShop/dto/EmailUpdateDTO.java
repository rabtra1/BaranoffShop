package ru.rabtra.baranoffShop.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailUpdateDTO {
    @Email(message = "Вы ввели неверный email")
    private String email;
}
