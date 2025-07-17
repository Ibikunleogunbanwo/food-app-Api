package com.Afrochow.food_app.response_dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String preferredDeliveryTime;
    private Integer loyaltyPoints;

    private AddressDTO address;
}