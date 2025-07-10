package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String storeId;

    private String storeName;
    private String storeDescription;
    private String storeLogo;

    private String streetAddress;
    private String storeCity;
    private String storeProvince;
    private String storePostalCode;
    private String storeCountry;
    private String storePhoneNumber;
    private String storeCategory;
    private String storeHours;
    private String maxDeliveryDistance;

    private boolean pickupAvailable;
    private boolean deliveryAvailable;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Many stores belong to one vendor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendorId") // FK in 'stores' table
    private Vendor vendor;

    // One store can have many products
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}