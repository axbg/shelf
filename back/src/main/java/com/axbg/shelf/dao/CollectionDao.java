package com.axbg.shelf.dao;

import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CollectionDao extends CrudRepository<Collection, Long> {
    List<Collection> findAllByUser(User user);

    @Query("SELECT c FROM Collection c where c.name = ?1 and c.user = ?2")
    Optional<Collection> findByName(String name, User user);

    void deleteByName(String name);
}
