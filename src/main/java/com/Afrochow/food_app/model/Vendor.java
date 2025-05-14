package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "business_owner_profiles")
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String businessOwnerId;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String streetAddress;
    private String country;
    private String apartmentNumber;
    private String city;
    private String province;
    private String postalCode;
    private String phoneNumber;
    private String idCardFront;
    private String idCardBack;
    private String profilePhoto;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Vendor() {
        this.createdAt = LocalDateTime.now();
    }

}