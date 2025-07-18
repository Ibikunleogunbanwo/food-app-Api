package com.Afrochow.food_app.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductRegistration {

    @NotEmpty(message = "Store Code cannot be empty")
    private String storeCode;

    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    @NotEmpty(message = "Product description cannot be empty")
    @Size(max = 500, message = "Product description must be between 10 and 80 characters")
    private String productDescription;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal basePrice;

    @NotNull(message = "Product image is required")
    private String productImage;

    @NotEmpty(message = "Product category cannot be empty")
    private String productCategory;
}