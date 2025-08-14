package ru.rabtra.baranoffShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneUpdateDTO {
    @NotBlank(message = "Поле ввода не может быть пустым")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", message = "Вы ввели неверный номер телефона")
    private String phone;
}
