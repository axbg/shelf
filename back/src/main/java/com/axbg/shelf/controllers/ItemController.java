package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.ItemService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<Item>> getItems(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(itemService.findAllByUserAndPage(page, size), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Item>> searchItem(@RequestParam String title) {
        return new ResponseEntity<>(itemService.searchByName(title), HttpStatus.OK);
    }

    @GetMapping(path = "/check")
    public ResponseEntity<String> isItemPresent(@RequestParam Map<String, String> request) {
        return itemService.isPresentByUrl(request.get("url")) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Map<String, String> request) throws CustomException {
        return new ResponseEntity<>(itemService.createItem(request.get("url")), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
