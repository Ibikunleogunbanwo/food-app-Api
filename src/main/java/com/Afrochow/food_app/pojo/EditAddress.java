package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.POSTAL_CODE_VALIDATION;

@Data
public class EditAddress {

    @NotEmpty(message = "Country is required")
    private String country;

    @NotEmpty(message = "Street Address is required")
    @Size(max = 100)
    private String streetAddress;

    @Size(max = 20)
    private String apartmentNumber;

    @NotEmpty(message = "Province is required")
    private String province;

    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "Postal code is required")
    @Pattern(
            regexp = POSTAL_CODE_VALIDATION,
            message = "Enter a valid Canadian postal code (e.g., K1A 0B1)"
    )
    private String postalCode;
}