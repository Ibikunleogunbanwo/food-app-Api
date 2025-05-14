package com.Afrochow.food_app.repository;


import com.Afrochow.food_app.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepo extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmailAddress (String sellerEmailAddress);
    Optional<Vendor> findByPhoneNumber (String sellerPhoneNumber);
    Optional<Vendor> findByBusinessOwnerId (String businessOwnerId);
    List<Vendor> findAllByOrderByBusinessOwnerIdDesc();




}
