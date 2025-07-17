package com.Afrochow.food_app.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductId implements Serializable {
    private Long storeId;
    private Long productId;
}