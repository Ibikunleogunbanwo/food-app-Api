package com.Afrochow.food_app.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class EditProduct {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be at most 100 characters")
    private String productName;

    @NotBlank(message = "Product description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String productDescription;

    private String productImage;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0",  message = "Base price must be greater than zero")
    private BigDecimal basePrice;
}