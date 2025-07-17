package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Eagerly fetch Store to avoid lazy loading issues
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_code", nullable = false)
    private Store store;

    // Eagerly fetch Product to avoid lazy loading issues
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer stock;

    private Double price;

    public StoreProduct(Store store, Product product) {
        this.store = store;
        this.product = product;
        this.price = 0.0;
        this.stock = 0;  // optional default
    }
}