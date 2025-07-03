package com.crackint.Shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crackint.Shop.model.Product;
import com.crackint.Shop.model.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Custom query to fetch products for a specific user
    List<Product> findByUser(User user);
}
