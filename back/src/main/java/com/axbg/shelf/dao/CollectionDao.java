package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Collection;
import org.springframework.data.repository.CrudRepository;

public interface CollectionDao extends CrudRepository<Collection, Long> {
}
