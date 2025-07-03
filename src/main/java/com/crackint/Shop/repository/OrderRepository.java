package com.crackint.Shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crackint.Shop.model.Order;
import com.crackint.Shop.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Correct: based on the User object
    List<Order> findByUser(User user);
}

