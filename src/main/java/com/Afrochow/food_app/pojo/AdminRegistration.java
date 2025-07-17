package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRegistration extends UserRegistration {

    @NotBlank(message = "Admin role is required")
    private String role;

}

