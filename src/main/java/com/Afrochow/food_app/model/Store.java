package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stores_management")
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeId;
    private String businessOwnerId;
    private String storeLogo;
    private String storeName;
    private String storeDescription;
    private String streetAddress;
    private String storeCity;
    private String storePostalCode;
    private String storeProvince;
    private String storeCountry;
    private String storePhoneNumber;
    private String storeCategory;
    private String storeHours;
    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public Store() {
        this.createdAt = LocalDateTime.now();
    }

}

//DEFINES THE STRUCTURE OF THE TABLE AND CREATES THE TABLE