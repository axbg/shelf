package com.axbg.shelf.services;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAllByUser();

    Optional<Item> findByIdAndUser(long id);

    List<Item> searchByName(String title);

    boolean isPresentByUrl(String url);

    Item createItem(String url) throws CustomException;
}