package com.crackint.Shop.controller;

import com.crackint.Shop.model.Order;
import com.crackint.Shop.service.OrderService;
import com.crackint.Shop.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Place a new order using JWT (secure)
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order,
                                        @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract username from JWT token
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);

            Order savedOrder = orderService.placeOrder(order, username);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(403)
                    .body("❌ Failed to place order: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserOrdersFromToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);

            return ResponseEntity.ok(orderService.getOrdersByUsername(username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(403)
                    .body("❌ Failed to fetch orders: " + e.getMessage());
        }
    }

}
