package com.Afrochow.food_app.pojo;

import com.Afrochow.food_app.response_dto.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StoreRegistration {

    @NotEmpty(message = "Business Owner Id cannot be empty")
    private String vendorCode;

    private String storeLogo;

    @NotEmpty(message = "Store name cannot be empty")
    private String storeName;

    @NotEmpty(message = "Store description cannot be empty")
    private String storeDescription;

    @NotEmpty(message = "Store category cannot be empty")
    private String storeCategory;

    @NotEmpty(message = "Store hours cannot be empty")
    private String storeOpeningHours;

    @NotEmpty(message = "Store hours cannot be empty")
    private String storeClosingHours;

    private String maxDeliveryDistance;

    @NotNull(message = "Pickup availability is required")
    private Boolean pickupAvailable;

    @NotNull(message = "Delivery availability is required")
    private Boolean deliveryAvailable;

    @Valid
    @NotNull(message = "Store address is required")
    private UserAddress address;
}