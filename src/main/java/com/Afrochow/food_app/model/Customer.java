package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("Customer")
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {


    private String customerCode;
    private Integer loyaltyPoints;

    @Column(name = "preferred_delivery_time")
    private String preferredDeliveryTime;


}