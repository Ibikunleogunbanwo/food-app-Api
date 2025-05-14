package com.Afrochow.food_app.pojo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.PHONE_NUMBER_VALIDATION;
import static com.Afrochow.food_app.config.AppConstant.POSTAL_CODE_VALIDATION;

@Data
public class UpdateVendorProfile {

    @NotEmpty(message = "Vendor Id Cannot be Empty")
    private String businessOwnerId;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String country;
    private String apartmentNumber;
    private String city;
    private String province;

    @Pattern(
            regexp = POSTAL_CODE_VALIDATION,
            message = "Enter a valid Canadian postal code (e.g., K1A 0B1)"
    )
    private String postalCode;

    @Pattern(
            regexp = PHONE_NUMBER_VALIDATION,
            message = "Enter a valid 10 Digit Phone Number (e.g. 1234567890)"
    )
    private String phoneNumber;
    private String idCardFront;
    private String idCardBack;
    private String profilePhoto;

}
