package com.Afrochow.food_app.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductData {

    @NotEmpty(message = "StoreId Cannot be Empty")
    private String storeId;

    @NotEmpty(message = "Product Name Cannot be Empty")
    private String productName;

    @NotEmpty(message = "Product description cannot be empty")
    @Size(min = 10, max = 80, message = "Product description must be between 10 and 80 characters")
    private String productDescription;

    @NotEmpty(message = "product Price Cannot be Empty")
    private float productPrice;

    @NotEmpty(message = "product Image Cannot be Empty")
    private String productImage;

}
