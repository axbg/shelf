package com.axbg.shelf.services;

import com.axbg.shelf.dao.UserDao;
import com.axbg.shelf.entity.User;
import com.axbg.shelf.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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

        return userDao.save(new User(email, firstName));
    }
}
