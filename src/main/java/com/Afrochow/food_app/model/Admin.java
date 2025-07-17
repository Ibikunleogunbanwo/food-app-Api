package com.Afrochow.food_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {

    @Column(name = "role", nullable = false)
    private String role;

}