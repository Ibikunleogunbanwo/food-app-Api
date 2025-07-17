package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByIdDesc();

    Optional<Customer> findCustomerById(Long userId);
    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);
}