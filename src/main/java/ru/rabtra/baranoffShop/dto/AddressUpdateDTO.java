package ru.rabtra.baranoffShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressUpdateDTO {

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

}
