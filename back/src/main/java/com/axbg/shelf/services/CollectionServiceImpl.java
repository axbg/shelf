package com.axbg.shelf.services;

import com.axbg.shelf.dao.CollectionDao;
import com.axbg.shelf.entity.Collection;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        return collectionDao.findByName(name).orElse(null);
    }

    @Override
    public void deleteByName(String name) {
        collectionDao.deleteByName(name);
    }
}
