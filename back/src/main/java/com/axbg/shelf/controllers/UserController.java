package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.axbg.shelf.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getProfile() {
        return new ResponseEntity<>(userService.findUser(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> request, HttpServletResponse response) throws CustomException {
        String jwt = userService.verifyGoogleAccount(request.get("gToken"));
        response.addCookie(JwtUtils.generateJwtCookie(jwt));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        userService.updateJwtReset();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        response.addCookie(JwtUtils.generateJwtCookie(""));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> remove() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
