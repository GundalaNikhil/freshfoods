package com.freshfoods.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double paymentAmount;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private String transactionId;
    private String invoiceDoc;
    private Date createdAt;
    private Date updatedAt;

}
