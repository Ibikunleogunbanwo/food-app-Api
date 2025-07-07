package com.Afrochow.food_app.DTO;


import lombok.Data;

@Data
public class StoreDto {

    private String storeLogo;
    private String storeName;
    private String storeDescription;
    private String streetAddress;
    private String storeCity;
    private String storePostalCode;
    private String storeCountry;
    private String storePhoneNumber;
    private String storeCategory;
    private String storeHours;
    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;

}

//TRANSFER DATA TO FRONTEND (RESPONSE PAYLOAD)