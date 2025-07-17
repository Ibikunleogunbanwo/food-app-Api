package com.Afrochow.food_app.response_dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String apartmentNumber;
    private String streetAddress;
    private String city;
    private String province;
    private String postalCode;
    private String country;
}