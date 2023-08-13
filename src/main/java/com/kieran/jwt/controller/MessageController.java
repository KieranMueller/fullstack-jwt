package com.kieran.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
public class MessageController {

    @GetMapping("/messages")
    public List<String> messages() {
        return Arrays.asList("top secret message #1", "top secret message #2");
    }
}
