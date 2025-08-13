package ru.rabtra.baranoffShop.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NameUpdateDTO {


    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно состоять минимум из 2-х символов и максимум из 50-ти символов")
    private String firstName;


    @NotBlank(message = "Фамилия не должна быть пустым")
    @Size(min = 2, max = 50, message = "Фамилия должна состоять минимум из 2-х символов и максимум из 50-ти символов")
    private String lastName;

}
