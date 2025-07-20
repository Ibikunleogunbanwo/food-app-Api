package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Customer;
import com.Afrochow.food_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByIdDesc();

    Optional<Customer> findCustomerById(Long userId);
    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);
    Optional<Customer> findCustomerByCustomerCode(String customerCode);

    Optional<Customer> findByUserCode(String userCode);
}