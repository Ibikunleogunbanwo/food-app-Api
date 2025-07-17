package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", unique = true, nullable = false)
    private String productCode;

    private String storeCode;


    private String productName;

    @Column(nullable = false)
    private String productDescription;
    private String productImage;
    private String category;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    // Many products belong to one vendor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    // One product can be available in multiple stores via StoreProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreProduct> availableInStores = new ArrayList<>();


    public void addStore(Store store) {
        StoreProduct storeProduct = new StoreProduct(store, this);
        availableInStores.add(storeProduct);
        store.getAvailableProducts().add(storeProduct);
    }

    // Helper: Remove this product from a store
    public void removeStore(Store store) {
        availableInStores.removeIf(sp -> sp.getStore().equals(store));
        store.getAvailableProducts().removeIf(sp -> sp.getProduct().equals(this));
    }
}