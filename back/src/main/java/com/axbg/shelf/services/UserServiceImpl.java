package com.axbg.shelf.services;

import com.axbg.shelf.dao.CollectionDao;
import com.axbg.shelf.dao.UserDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.security.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static String clientId;
    private final UserDao userDao;
    private final CollectionDao collectionDao;

    @Override
    public User findUser() {
        return findUserByEmail(getUserEmail()).orElse(null);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void registerUser(String email, String firstName, String photo) throws CustomException {
        if (email.isBlank() || firstName.isBlank()) {
            throw new CustomException("Required fields are empty", HttpStatus.BAD_REQUEST);
        }

        User toSave = new User(email, firstName, photo);
        User user = userDao.save(toSave);

        Collection collection = Collection.builder().name(UNSORTED_COLLECTION).build();
        collectionDao.save(collection);

        collection.setUser(user);
    }

    @Override
    public String verifyGoogleAccount(final String gToken) throws CustomException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        try {
            GoogleIdToken gId = verifier.verify(gToken);

            if (gId != null) {
                GoogleIdToken.Payload payload = gId.getPayload();
                String email = payload.getEmail();
                String picture = (String) payload.get("picture");
                Optional<User> user = findUserByEmail(email);

                if (user.isEmpty()) {
                    registerUser(email, (String) payload.get("name"), picture);
                } else if (picture != null && !picture.isBlank()) {
                    user.get().setPhoto(picture);
                }

                return JwtUtils.generateJwt(email);
            }
            throw new Exception();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
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

    @Value("${shelf.app.google.id}")
    private void setClientId(String id) {
        clientId = id;
    }
}
