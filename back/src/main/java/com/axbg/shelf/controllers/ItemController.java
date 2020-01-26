package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @GetMapping(value = "/")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
