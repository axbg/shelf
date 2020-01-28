package com.axbg.shelf.services;

import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;

public interface UserService {
    User findUser();

    User registerUser(String email, String firstName) throws CustomException;

    String verifyGoogleAccount(String gToken) throws CustomException;

    void deleteUser();
}
