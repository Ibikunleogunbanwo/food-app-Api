package com.Afrochow.food_app.response_dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String productCode;
    private String productName;
    private String productDescription;
    private String productImage;
    private String productCategory;
    private String basePrice;
}
