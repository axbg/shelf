package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.Item;
import com.axbg.shelf.services.CollectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<Collection>> getCollections() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Collection> getCollection(@RequestParam long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(params = "collection")
    ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(params = "item")
    ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<String> deleteCollection(@RequestParam("id") long id) {
        //delete only if name != UNSORTED_COLLECTION
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
