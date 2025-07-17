package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("VENDOR")
public class Vendor extends User {

    @Column(unique = true, nullable = false)
    private String vendorId;

    @Column(unique = true)
    private String businessLicenseNumber;

    private String businessName;
    private String taxId;

    @Column(length = 512)
    private String idCardFrontUrl;

    @Column(length = 512)
    private String idCardBackUrl;

    @Column(length = 512)
    private String businessLogoUrl;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public void addStore(Store store) {
        stores.add(store);
        store.setVendor(this);
    }

    public void removeStore(Store store) {
        stores.remove(store);
        store.setVendor(null);
    }
}