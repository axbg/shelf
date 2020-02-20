package com.axbg.shelf.services;

import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import java.util.Optional;

public interface UserService {
    User findUser();

    Optional<User> findUserByEmail(String email);

    void registerUser(String email, String firstName, String photo) throws CustomException;

    String verifyGoogleAccount(String gToken) throws CustomException;

    void updateJwtReset();

    void deleteUser();
}
