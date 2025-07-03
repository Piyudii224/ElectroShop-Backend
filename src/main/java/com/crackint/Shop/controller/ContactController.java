package com.crackint.Shop.controller;

import com.crackint.Shop.model.ContactRequest;
import com.crackint.Shop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@RequestBody ContactRequest request) {
        emailService.sendContactEmail(request.getName(), request.getEmail(), request.getMessage());
        return ResponseEntity.ok("Message sent successfully");
    }
}
