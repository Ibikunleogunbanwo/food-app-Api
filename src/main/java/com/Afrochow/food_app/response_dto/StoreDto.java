package com.Afrochow.food_app.response_dto;


import lombok.Data;

@Data
public class StoreDto {
    private String storeId;
    private String storeName;
    private String storeDescription;
    private String storeLogo;
    private String storePhoneNumber;
    private String storeCategory;

    private String maxDeliveryDistance;
    private boolean pickupAvailable;
    private boolean deliveryAvailable;

    private String storeOpeningHours;
    private String storeClosingHours;

    private AddressDTO address;
}

//TRANSFER DATA TO FRONTEND (RESPONSE PAYLOAD)