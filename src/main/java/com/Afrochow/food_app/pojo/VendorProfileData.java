package com.Afrochow.food_app.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

import static com.Afrochow.food_app.config.AppConstant.*;

@Data
public class VendorProfileData {

    @NotEmpty(message = "First Name Cannot be Empty")
    private String firstName;

    @NotEmpty (message = "Last Name Cannot be empty")
    private String lastName;

    @NotEmpty(message = "Email cannot be Empty")
    @Email (message = "Enter a valid email address")
    private String emailAddress;

    @NotEmpty(message = "Password is Required")
    @Pattern(regexp = PASSWORD_PATTERN,
            message = "password must contain 1 lower case, 1 uppercase, 1 special character, 1 number and at least 6 character length ")
    private String password;

    @NotEmpty(message = "Country is Required")
    private String country;

    @NotEmpty(message = "Street Address is Required")
    private String streetAddress;

    @NotEmpty(message = "Apartment Number is Required")
    private String apartmentNumber;

    @NotEmpty(message = "City is Required")
    private String city;

    @NotEmpty(message = "Province is Required")
    private String province;

    @NotEmpty(message = "Postal code is required")
    @Pattern(
            regexp = POSTAL_CODE_VALIDATION,
            message = "Enter a valid Canadian postal code (e.g., K1A 0B1)"
    )
    private String postalCode;

    @NotEmpty(message = "Phone Number is required")
    @Pattern(
            regexp = PHONE_NUMBER_VALIDATION,
            message = "Enter a valid 10 Digit Phone Number (e.g., 1234567890)"
    )
    private String phoneNumber;

    private String idCardFront;

    private String idCardBack;

    private String profilePhoto;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime Updated_at;

}
