package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditAdmin extends EditUser {

    @NotBlank(message = "Role is required")
    private String role;
}