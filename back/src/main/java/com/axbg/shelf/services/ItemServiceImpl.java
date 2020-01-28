package com.axbg.shelf.services;

import com.axbg.shelf.dao.ItemDao;
import com.axbg.shelf.entity.Item;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Optional<Item> findById(final long id) {
        return itemDao.findById(id);
    }

    @Override
    public List<Item> findAllByUser() {
        return itemDao.findAllByUser(userService.findUser());
    }

    @Override
    public Optional<Item> findByIdAndUser(final long id) {
        Optional<Item> item = itemDao.findById(id);
        return item.isPresent() && item.get().getCollection().getUser().equals(userService.findUser()) ?
                item : Optional.empty();
    }
}
