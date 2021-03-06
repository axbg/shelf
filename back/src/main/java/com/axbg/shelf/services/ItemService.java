package com.axbg.shelf.services;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAllByUserAndPage(int page, int size);

    Optional<Item> findByIdAndUser(long id);

    List<Item> searchByName(String title);

    boolean isPresentByUrl(String url);

    Item createItem(String url, String title, String favicon);

    boolean updateItemTitle(long id, String title);

    Item scrapAndCreateItem(String url) throws CustomException;

    void deleteItem(long id);

    void deleteItemByUrl(String url);
}
