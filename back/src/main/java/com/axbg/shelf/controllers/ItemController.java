package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.ItemService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return new ResponseEntity<>(itemService.findAllByUser(), HttpStatus.OK);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Item> getItem(@RequestParam("id") long id) {
        Optional<Item> item = itemService.findByIdAndUser(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Map<String, String> request) throws CustomException {
        return new ResponseEntity<>(itemService.createItem(request.get("url")), HttpStatus.OK);
    }

    @PostMapping(path = "/check")
    public ResponseEntity<String> isItemPresent(@RequestBody Map<String, String> request) {
        return itemService.isPresentByUrl(request.get("url")) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<Item>> searchItem(@RequestBody Map<String, String> request) {
        return new ResponseEntity<>(itemService.searchByName(request.get("title")), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestParam("id") long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
