package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.CollectionService;
import com.axbg.shelf.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionService collectionService;
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Collection>> getCollections() {
        return new ResponseEntity<>(collectionService.findAll(), HttpStatus.OK);
    }

    @GetMapping(params = "name")
    public ResponseEntity<Collection> getCollection(@RequestParam String name) {
        Collection collection = collectionService.findByName(name);

        return collection != null ?
                new ResponseEntity<>(collection, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(params = "collection")
    ResponseEntity<Collection> createCollection(@RequestBody Collection collection) throws CustomException {
        try {
            return new ResponseEntity<>(collectionService.storeCollection(collection), HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException("A collection with this name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(params = "itemId")
    ResponseEntity<Item> addItem(@RequestBody long itemId) {
        Optional<Item> item = itemService.findByIdAndUser(itemId);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    ResponseEntity<String> deleteCollection(@RequestParam("name") String name) throws CustomException {
        collectionService.deleteByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
