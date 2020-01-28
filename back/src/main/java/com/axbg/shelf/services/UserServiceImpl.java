package com.axbg.shelf.services;

import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

import com.axbg.shelf.dao.UserDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${shelf.app.google.id}")
    private static String CLIENT_ID;

    private final UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

    @Override
    public User registerUser(String email, String firstName) throws CustomException {
        if (email.isBlank() || firstName.isBlank()) {
            throw new CustomException("Required fields are empty", HttpStatus.BAD_REQUEST);
        }

        User user = userDao.save(new User(email, firstName));
        user.setCollectionList(Collections.singletonList(Collection.builder().name(UNSORTED_COLLECTION).build()));
        return user;
    }

    @Override
    public String verifyGoogleAccount(final String gToken) throws CustomException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken gId = verifier.verify(gToken);

            if (gId != null) {
                GoogleIdToken.Payload payload = gId.getPayload();
                String email = payload.getEmail();
                User user = findByEmail(email);

                if (user == null) {
                    user = registerUser(email, (String) payload.get("name"));
                }

                return JwtUtils.generateJwt(user.getEmail());
            }
            throw new Exception();
        } catch (Exception ex) {
            throw new CustomException("Error on Google connection", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
