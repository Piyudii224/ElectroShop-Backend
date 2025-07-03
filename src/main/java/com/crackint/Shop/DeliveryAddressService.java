package com.crackint.Shop;

import java.util.List;

import com.crackint.Shop.dto.DeliveryAddressDTO;
import com.crackint.Shop.model.DeliveryAddress;

/**
 * Service interface for managing delivery addresses linked to users.
 */
public interface DeliveryAddressService {

    /**
     * Saves a new delivery address for a given user.
     *
     * @param userId the ID of the user the address belongs to
     * @param dto    the delivery address data transfer object
     * @return the saved DeliveryAddress entity
     */
    DeliveryAddress saveAddress(Long userId, DeliveryAddressDTO dto);

    /**
     * Retrieves all delivery addresses associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of DeliveryAddress entities
     */
    List<DeliveryAddress> getAddressesByUser(Long userId);

    /**
     * Deletes a delivery address by its ID.
     *
     * @param addressId the ID of the address to delete
     */
    void deleteAddress(Long addressId);
}
