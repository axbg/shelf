package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {
    @Query("SELECT i FROM Item i where i.collection.user = ?1")
    List<Item> findAllByUser(User user);

}
