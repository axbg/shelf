package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemDao extends CrudRepository<Item, Long> {
    @Query("SELECT i FROM Item i where i.id = ?1 and i.collection.user = ?2")
    Optional<Item> findByIdAndUser(long id, User user);

    @Query("SELECT i FROM Item i where i.collection.user = ?1")
    List<Item> findAllByUser(User user, Pageable pageable);

    @Query("SELECT i from Item i where i.collection.user = ?1 and i.title like %?2%")
    List<Item> findByUserAndTitleContaining(User user, String title);

    @Query("SELECT i from Item i where i.collection.user = ?1 and i.url = ?2")
    Optional<Item> findByUserAndUrl(User user, String url);

    void deleteItemById(long id);

    void deleteItemByUrl(String url);
}
