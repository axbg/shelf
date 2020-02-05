package com.axbg.shelf.controllers;

import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.axbg.shelf.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /* temporary workaround */
    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        try {
            userService.registerUser("dummy@dummy.com", "Dummy");
        } catch (Exception ignored) {
        }
        response.addCookie(JwtUtils.generateJwtCookie(JwtUtils.generateJwt("dummy@dummy.com")));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String gToken, HttpServletResponse response) throws CustomException {
        String jwt = userService.verifyGoogleAccount(gToken);
        response.addCookie(JwtUtils.generateJwtCookie(jwt));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        userService.updateJwtReset();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> remove() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
