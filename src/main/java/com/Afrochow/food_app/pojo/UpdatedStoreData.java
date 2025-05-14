package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.PHONE_NUMBER_VALIDATION;
import static com.Afrochow.food_app.config.AppConstant.POSTAL_CODE_VALIDATION;

@Data
public class UpdatedStoreData {

    @NotEmpty(message = "storeId cannot be Empty")
    private String storeId;

    @NotEmpty(message = "Business Owner Id Cannot be Empty")
    private String businessOwnerId;
    private String storeLogo;
    private String storeName;
    private String storeDescription;
    private String streetAddress;
    private String storeCity;

    @Pattern(
            regexp = POSTAL_CODE_VALIDATION,
            message = "Enter a valid Canadian postal code (e.g., K1A 0B1)"
    )
    private String storePostalCode;
    private String storeCountry;

    private String storeCategory;
    private String storeHours;
    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;


}
