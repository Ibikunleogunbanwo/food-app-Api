package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.*;

@Data
public class UserAddress {

    @NotEmpty(message = "Country is required")
    private String country;

    @NotEmpty(message = "Street Address is required")
    private String streetAddress;

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