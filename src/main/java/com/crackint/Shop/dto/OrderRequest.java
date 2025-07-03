package com.crackint.Shop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * DTO representing the full order request data from frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private Long addressId;
    private List<OrderItemRequest> items;
    private String paymentId;
    private double totalAmount;
}
