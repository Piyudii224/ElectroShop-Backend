package com.crackint.Shop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO representing a single item in an order request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
