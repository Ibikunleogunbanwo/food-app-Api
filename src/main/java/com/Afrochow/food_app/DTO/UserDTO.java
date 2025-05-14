package com.Afrochow.food_app.DTO;


import lombok.Data;

@Data
public class UserDTO {
    Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String country;
    private String streetAddress;
    private String apartmentNumber;
    private String province;
    private String city;
    private String postalCode;
    private String phoneNumber;
}
