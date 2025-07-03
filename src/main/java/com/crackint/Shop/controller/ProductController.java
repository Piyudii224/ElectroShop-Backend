package com.crackint.Shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crackint.Shop.model.Product;
import com.crackint.Shop.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")  // Allow Angular access
public class ProductController {

  @Autowired
  private ProductRepository productRepo;

  // ✅ Fetch all products
  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    try {
      List<Product> products = productRepo.findAll();
      return ResponseEntity.ok(products);
    } catch (Exception e) {
      return ResponseEntity.status(500).build(); // Internal Server Error
    }
  }

  // 🔍 Optional: Fetch product by ID
  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable Long id) {
      return productRepo.findById(id)
              .map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // 📌 You can also add filtering by name, category, price later
}
