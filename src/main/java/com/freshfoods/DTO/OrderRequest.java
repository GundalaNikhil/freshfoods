package com.freshfoods.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long userId;
    private Long addressId;
    private List<OrderItemRequest> items;
    private String paymentMethod;
}
