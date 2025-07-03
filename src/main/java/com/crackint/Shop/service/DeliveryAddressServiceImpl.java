package com.crackint.Shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.crackint.Shop.DeliveryAddressService;
import com.crackint.Shop.dto.DeliveryAddressDTO;
import com.crackint.Shop.model.DeliveryAddress;
import com.crackint.Shop.model.User;
import com.crackint.Shop.repository.*;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Autowired
    private DeliveryAddressRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public DeliveryAddress saveAddress(Long userId, DeliveryAddressDTO dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DeliveryAddress address = new DeliveryAddress();
        address.setUser(user);
        address.setFullName(dto.getFullName());
        address.setMobile(dto.getMobile());
        address.setHouseStreet(dto.getHouseStreet());
        address.setLandmark(dto.getLandmark());
        address.setTown(dto.getTown());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPinCode(dto.getPinCode());

        return repo.save(address);
    }

    @Override
    public List<DeliveryAddress> getAddressesByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return repo.findByUser(user); // ✅ Correct call
    }

    @Override
    public void deleteAddress(Long addressId) {
        repo.deleteById(addressId);
    }
}
