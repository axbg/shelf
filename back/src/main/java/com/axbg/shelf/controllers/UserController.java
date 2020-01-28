package com.axbg.shelf.controllers;

import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.axbg.shelf.services.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /* temporary workaround */
    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletResponse response) {
        String jwt = JwtUtils.generateJwt("dummy@dummy.com");
        Cookie jwtCookie = new Cookie("X-AUTH", jwt);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String gToken, HttpServletResponse response) throws CustomException {
        String jwt = userService.verifyGoogleAccount(gToken);

        Cookie jwtCookie = new Cookie("X-AUTH", jwt);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true);

        response.addCookie(jwtCookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> remove() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
