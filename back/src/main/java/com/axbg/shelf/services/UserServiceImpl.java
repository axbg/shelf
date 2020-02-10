package com.axbg.shelf.services;

import com.axbg.shelf.dao.UserDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${shelf.app.google.id}")
    private static String CLIENT_ID;

    private final UserDao userDao;

    @Override
    public User findUser() {
        return findUserByEmail(getUserEmail()).orElse(null);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void registerUser(String email, String firstName) throws CustomException {
        if (email.isBlank() || firstName.isBlank()) {
            throw new CustomException("Required fields are empty", HttpStatus.BAD_REQUEST);
        }

        User user = userDao.save(new User(email, firstName));
        user.setCollectionList(Collections.singletonList(Collection.builder().name(UNSORTED_COLLECTION).build()));
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
                Optional<User> user = findUserByEmail(email);

                if (user.isEmpty()) {
                    registerUser(email, (String) payload.get("name"));
                }

                return JwtUtils.generateJwt(email);
            }
            throw new Exception();
        } catch (Exception ex) {
            throw new CustomException("Error on Google connection", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateJwtReset() {
        userDao.findByEmail(getUserEmail()).ifPresent((user) -> user.setLastReset(Instant.now()));
    }

    @Override
    public void deleteUser() {
        userDao.deleteByEmail(getUserEmail());
    }

    private String getUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}