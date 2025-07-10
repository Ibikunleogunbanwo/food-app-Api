package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vendors")
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vendorId;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String streetAddress;
    private String apartmentNumber;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String idCardFront;
    private String idCardBack;
    private String profilePhoto;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> stores = new ArrayList<>();

    public Vendor() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}