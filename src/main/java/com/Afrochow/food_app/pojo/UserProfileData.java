package com.Afrochow.food_app.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

import static com.Afrochow.food_app.config.AppConstant.*;

@Data
public class UserProfileData {
    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String emailAddress;

    @NotEmpty(message = "Please Enter password")
    @Pattern(regexp = PASSWORD_PATTERN,
            message = "password must contain 1 lower case, 1 uppercase, 1 special character, 1 number and at least 6 character length ")
    private String password;

    @NotEmpty(message = "Country is required")
    private String country;

    @NotEmpty(message = "Address is required")
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

    @NotEmpty(message = "Phone Number is required")
    @Pattern(
            regexp = PHONE_NUMBER_VALIDATION,
            message = "Enter a valid 10 Digit Phone Number (e.g. 1234567890)"
    )
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime Updated_at;
}



