package com.axbg.shelf.controllers;

import com.axbg.shelf.security.Jwt;
import com.axbg.shelf.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/login")
    public ResponseEntity<Jwt> login() {
        return new ResponseEntity<>(Jwt.builder().token(JwtUtils.generateJwt("dummy@net.com")).build(), HttpStatus.OK);
    }
}
