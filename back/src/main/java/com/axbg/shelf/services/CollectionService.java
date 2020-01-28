package com.axbg.shelf.services;

import com.axbg.shelf.entity.Collection;
import java.util.List;

public interface CollectionService {
    Collection storeCollection(Collection collection);

    List<Collection> findAll();

    Collection findByName(String name);

    void deleteByName(String name);
}
