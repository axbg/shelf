package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {
    @Query("SELECT i FROM Item i where i.collection.user = ?1")
    List<Item> findAllByUser(User user);

    @Query("SELECT i from Item i where i.collection.user = ?1 and i.title like %?2%")
    List<Item> findByUserAndTitleContaining(User user, String title);
}
