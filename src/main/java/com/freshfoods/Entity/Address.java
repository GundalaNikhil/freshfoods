package com.freshfoods.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String street1;
    private String street2;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String altPhone;
    private boolean isDefault;
    private Date createdAt;
    private Date updatedAt;

}