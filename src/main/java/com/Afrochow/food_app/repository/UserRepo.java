package com.Afrochow.food_app.repository;

import com.Afrochow.food_app.model.Customer;
import com.Afrochow.food_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional <User> findByPhoneNumber (String userPhoneNumber);
    List<User> findAllByOrderByIdDesc();




    @Query(value = "SELECT u FROM User u order by u.id desc")
    List <User> listAllUsers ();

}


