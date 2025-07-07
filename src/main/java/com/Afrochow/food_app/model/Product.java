package com.Afrochow.food_app.model;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeId;
    private String productId;
    private String productName;
    private String productDescription;
    private float productPrice;
    private String productImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product() {
        this.createdAt = LocalDateTime.now();
    }

}
