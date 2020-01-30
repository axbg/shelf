package com.axbg.shelf.services;

import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

import com.axbg.shelf.dao.CollectionDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.exception.CustomException;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
