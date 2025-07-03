package com.crackint.Shop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication; // ✅ Correct
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.crackint.Shop.model.Order;
import com.crackint.Shop.model.OrderItem;
import com.crackint.Shop.model.User;
import com.crackint.Shop.repository.OrderRepository;
import com.crackint.Shop.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
   
    
    public Order placeOrder(Order order, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        // ⚠️ Fix: Assign 'order' reference to each OrderItem
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        } else {
            throw new RuntimeException("Order items cannot be null or empty");
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

}
