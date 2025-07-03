package com.crackint.Shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crackint.Shop.model.DeliveryAddress;
import com.crackint.Shop.model.User;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    // ✅ Correct way: using User object
    List<DeliveryAddress> findByUser(User user);
}
