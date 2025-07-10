package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.POSTAL_CODE_VALIDATION;

@Data
public class StoreData {

    @NotEmpty(message = "Business Owner Id Cannot be Empty")
    private String vendorId;

    private String storeLogo;

    @NotEmpty(message = "Store name Cannot be Empty")
    private String storeName;

    @NotEmpty(message = "Store Description Cannot be Empty")
    private String storeDescription;

    @NotEmpty(message = "Store Address is required")
    private String streetAddress;

    @NotEmpty(message = "City is required")
    private String storeCity;

    @NotEmpty(message = "Store Postal code is required")
    @Pattern(
            regexp = POSTAL_CODE_VALIDATION,
            message = "Enter a valid Canadian postal code (e.g., K1A 0B1)"
    )
    private String storePostalCode;

    @NotEmpty(message = "Province is required")
    private String storeProvince;

    @NotEmpty(message = "Country is required")
    private String storeCountry;

    @NotEmpty(message = "Store Category Cannot be Empty")
    private String storeCategory;

    @NotEmpty(message = "Store Hours Cannot be Empty")
    private String storeHours;

    private String maxDeliveryDistance;

    @NotNull(message = "Pickup availability is required")
    private Boolean pickupAvailable;

    @NotNull(message = "Delivery availability is required")
    private Boolean deliveryAvailable;
}