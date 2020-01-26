package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.axbg.shelf.services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private static GoogleIdTokenVerifier verifier;

    @Value("${shelf.app.google.id}")
    private static String CLIENT_ID;

    private final UserService userService;

    static {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String gToken, HttpServletResponse response) throws CustomException {
        try {
            GoogleIdToken gId = verifier.verify(gToken);

            if (gId != null) {
                GoogleIdToken.Payload payload = gId.getPayload();
                String email = payload.getEmail();

                User user = userService.findByEmail(email);

                String jwt;
                if (user != null) {
                    jwt = JwtUtils.generateJwt(user.getEmail());
                } else {
                    User newUser = userService.registerUser(email, (String) payload.get("name"));
                    jwt = JwtUtils.generateJwt(newUser.getEmail());
                }

                Cookie jwtCookie = new Cookie("X-AUTH", jwt);
                jwtCookie.setPath("/");
                jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true);

                response.addCookie(jwtCookie);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            throw new Exception();
        } catch (Exception e) {
            throw new CustomException("Oops. An error happened", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
