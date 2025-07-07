package com.Afrochow.food_app.DTO;

import lombok.Data;

@Data
public class ProductDTO {

    private String storeId;
    private String productId;
    private String productName;
    private String productDescription;
    private float productPrice;
    private String productImage;

}
