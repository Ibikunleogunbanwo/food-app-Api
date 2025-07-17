package com.Afrochow.food_app.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.*;

@Data
public class UserRegistration {

    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotEmpty(message = "Please Enter password")
    @Pattern(
            regexp = PASSWORD_PATTERN,
            message = "Password must contain 1 lowercase, 1 uppercase, 1 special character, 1 number and be at least 6 characters long"
    )
    private String password;

    @NotEmpty(message = "Phone Number is required")
    @Pattern(
            regexp = PHONE_NUMBER_VALIDATION,
            message = "Enter a valid 10 Digit Phone Number (e.g. 1234567890)"
    )
    private String phoneNumber;

    @NotNull(message = "Address is required")
    @Valid
    private UserAddress address;
}