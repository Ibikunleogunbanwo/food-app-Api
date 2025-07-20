package com.Afrochow.food_app.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditStore {

    @NotBlank(message = "Store ID cannot be empty")
    private String storeCode;

    @NotBlank(message = "Store ID cannot be empty")
    private String vendorCode;

    @NotBlank(message = "Store name is required")
    @Size(max = 100, message = "Store name must be at most 100 characters")
    private String storeName;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String storeDescription;

    private String storeLogo;

    @Size(max = 20, message = "Phone number must be at most 20 characters")
    private String storePhoneNumber;

    private String storeCategory;

    // Using strings for time to accept values like "08:00", "22:00"
    private String storeOpeningHours;
    private String storeClosingHours;

    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;

    @Valid
    private EditAddress address;
}