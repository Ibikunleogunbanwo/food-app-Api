package com.Afrochow.food_app.pojo;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerRegistration extends UserRegistration {

    private Integer loyaltyPoints;

    private String preferredDeliveryTime;
}