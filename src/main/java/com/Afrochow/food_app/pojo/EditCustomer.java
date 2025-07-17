package com.Afrochow.food_app.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditCustomer extends EditUser {

    private Integer loyaltyPoints;

    private String preferredDeliveryTime;
}
