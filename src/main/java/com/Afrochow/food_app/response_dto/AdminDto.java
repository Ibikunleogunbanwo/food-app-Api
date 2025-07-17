package com.Afrochow.food_app.response_dto;

import lombok.Data;

@Data
public class AdminDto {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String role; // e.g. "SUPER_ADMIN", "MODERATOR", etc.

    private AddressDTO address;
}
