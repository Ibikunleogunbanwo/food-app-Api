package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_code", unique = true, nullable = false)
    private String storeCode;

    @Column(nullable = false)
    private String storeName;

    private String storeDescription;
    private String storeLogo;
    private String storePhoneNumber;
    private String storeCategory;

    private LocalTime storeOpeningHours;
    private LocalTime storeClosingHours;

    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;

    @Column(name = "vendor_code", nullable = false)
    private String vendorCode;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
    private Vendor vendor;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StoreProduct> availableProducts = new ArrayList<>();

    // Utility methods
    public void addProduct(Product product) {
        StoreProduct storeProduct = new StoreProduct(this, product);
        availableProducts.add(storeProduct);
        product.getAvailableInStores().add(storeProduct);
    }

    public void removeProduct(Product product) {
        availableProducts.removeIf(sp -> sp.getProduct().equals(product));
        product.getAvailableInStores().removeIf(sp -> sp.getStore().equals(this));
    }
}