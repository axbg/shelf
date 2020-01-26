package com.axbg.shelf.controllers;

import com.axbg.shelf.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        String jwt = JwtUtils.generateJwt("dummy@net.com");
        Cookie jwtCookie = new Cookie("X-AUTH", jwt);

        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true);

        response.addCookie(jwtCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
