package ru.rabtra.baranoffShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordUpdateDTO {

    @NotBlank(message = "Пароль не должен быть пустым")
    private String currentPassword;

    @NotBlank(message = "Новый пароль не должен быть пустым")
    @Size(min = 8, max=100, message = "Новый пароль должен содержать минимум 8 символов и максимум 100 символов")
    private String newPassword;

    private String confirmNewPassword;

}
