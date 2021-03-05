package com.serverapplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    String temp_name = "swdede123";
    String temp_password = "a142536";

    @GetMapping
    @CrossOrigin
    ResponseEntity<Boolean> isValid(@RequestParam(value = "username") String name,
                           @RequestParam(value = "password") String password) {
        if (name.equals(temp_name) && password.equals(temp_password)) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
