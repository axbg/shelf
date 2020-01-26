package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {
}
