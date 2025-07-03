package com.crackint.Shop.config;

import com.crackint.Shop.model.Product;
import com.crackint.Shop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                List<Product> products = List.of(
                    new Product(
                        "Wireless Headphones",
                        "High quality sound with noise cancellation.",
                        1999,
                        "https://img.freepik.com/premium-photo/black-wireless-headphones-isolated-black-background_95544-15.jpg"
                    ),
                    new Product(
                        "Smartwatch",
                        "Track your fitness and get notifications on the go.",
                        4999,
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwPxoj0gxKwpdVyr_gq2BCaCZ_2RK6TM-N0Q&s"
                    ),
                    new Product(
                        "Bluetooth Speaker",
                        "Crisp audio with long battery life.",
                        1999,
                        "https://wallpapers.com/images/hd/beats-pill-speaker-black-background-d5gf4ly20pvdltxt.jpg"
                    ),
                    new Product(
                        "Portable Power Bank",
                        "Charge your devices on the go.",
                        1299,
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYB0I0g4jv-Y9NPlXbL0nepalwEVn-FkyFJA&s"
                    ),
                    new Product(
                        "AirPods Pro",
                        "Premium wireless earbuds with spatial audio.",
                        18999,
                        "https://cdn.mos.cms.futurecdn.net/TwjAZs6b3QXn7SGxjg6rQ7.jpg"
                    ),
                    new Product(
                        "USB-C Hub",
                        "Multiport adapter for laptops and tablets.",
                        2299,
                        "https://www.lc-power.com/wp-content/uploads/2024/04/Galerie-LC-HUB-C-MULTI-7-M2-03.jpg"
                    )
                );

                repo.saveAll(products);
                logger.info("✅ Sample products inserted.");
            } else {
                logger.info("ℹ️ Products already initialized. Skipping sample insertion.");
            }
        };
    }
}
