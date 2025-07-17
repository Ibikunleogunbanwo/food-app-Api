package com.Afrochow.food_app.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.PHONE_NUMBER_VALIDATION;

@Data
public class EditUser {

    @NotEmpty(message = "User ID cannot be empty")
    private String userId;

    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @Pattern(
            regexp = PHONE_NUMBER_VALIDATION,
            message = "Enter a valid 10-digit phone number (e.g., 1234567890)"
    )
    private String phoneNumber;

    @Valid
    private EditAddress address;

}