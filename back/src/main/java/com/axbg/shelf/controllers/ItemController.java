package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.services.ItemService;
import java.util.ArrayList;
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
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Item> getItem(@RequestParam("id") long id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(params = "title")
    public ResponseEntity<List<Item>> searchItem(@RequestBody String title) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PostMapping(params = "item")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return new ResponseEntity<>(new Item(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestParam("id") long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
