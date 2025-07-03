package com.crackint.Shop.controller;

import com.crackint.Shop.DeliveryAddressService;
import com.crackint.Shop.dto.DeliveryAddressDTO;
import com.crackint.Shop.model.DeliveryAddress;
import com.crackint.Shop.model.User;
import com.crackint.Shop.repository.UserRepository;
import com.crackint.Shop.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add new address for a user
    @PostMapping("/{userId}/address")
    public ResponseEntity<?> addAddress(@PathVariable Long userId,
                                        @RequestBody DeliveryAddressDTO dto) {
        try {
            DeliveryAddress saved = service.saveAddress(userId, dto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add address: " + e.getMessage());
        }
    }

    // ✅ Get all addresses by userId
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<?> getAddresses(@PathVariable Long userId) {
        try {
            List<DeliveryAddress> addresses = service.getAddressesByUser(userId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Failed to retrieve addresses: " + e.getMessage());
        }
    }

    // ✅ Delete address by addressId
    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        try {
            service.deleteAddress(id);
            return ResponseEntity.ok("Address deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete address: " + e.getMessage());
        }
    }

    // 🔐 Get addresses from JWT token
    @GetMapping("/addresses")
    public ResponseEntity<?> getMyAddresses(@RequestHeader("Authorization") String authHeader) {
        try {
            System.out.println("🔐 Received Authorization header: " + authHeader); // DEBUG

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }

            String token = authHeader.replace("Bearer ", "").trim();
            System.out.println("🔐 Extracted token: " + token); // DEBUG

            if (token.split("\\.").length != 3) {
                throw new RuntimeException("Malformed JWT token");
            }

            String username = jwtUtil.extractUsername(token);

            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

            List<DeliveryAddress> addresses = service.getAddressesByUser(user.getId());
            return ResponseEntity.ok(addresses);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized: " + e.getMessage());
        }
    }
}