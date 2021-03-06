package com.axbg.shelf.services.impl;

import com.axbg.shelf.dao.CollectionDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.CollectionService;
import com.axbg.shelf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {
    private final CollectionDao collectionDao;
    private final UserService userService;

    @Override
    public Collection storeCollection(final Collection collection) throws DataIntegrityViolationException {
        collection.setId(null);
        collection.setUser(userService.findUser());
        collectionDao.save(collection);
        return collection;
    }

    @Override
    public List<Collection> findAll() {
        return collectionDao.findAllByUser(userService.findUser());
    }

    @Override
    public Collection findByName(final String name) {
        return collectionDao.findByName(name, userService.findUser()).orElse(null);
    }

    @Override
    public void deleteByName(String name) throws CustomException {
        if (name.equals(UNSORTED_COLLECTION)) {
            throw new CustomException("Unsorted list cannot be deleted", HttpStatus.BAD_REQUEST);
        }

        collectionDao.deleteByName(name);
    }
}
