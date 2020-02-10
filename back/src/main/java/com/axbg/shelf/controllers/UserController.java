package com.axbg.shelf.controllers;

import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.axbg.shelf.services.UserService;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request, HttpServletResponse response) throws CustomException {
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
