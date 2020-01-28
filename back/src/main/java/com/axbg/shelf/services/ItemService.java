package com.axbg.shelf.services;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.entity.User;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<Item> findById(long id);

    List<Item> findAllByUser();

    Optional<Item> findByIdAndUser(long id);
}
