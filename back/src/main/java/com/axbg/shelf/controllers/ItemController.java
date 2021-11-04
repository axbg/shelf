package com.axbg.shelf.controllers;

import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
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
        validateCreateItemRequest(request);
        return new ResponseEntity<>(itemService.createItem(request.get("url"), request.get("title"), request.get("photo")), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateTitle(@RequestBody Map<String, String> request) throws CustomException {
        validateUpdateItemRequest(request);

        HttpStatus status = itemService.updateItemTitle(Long.parseLong(request.get("id")), request.get("title"))
                ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(status);
    }

    @PostMapping(path = "/scrap")
    public ResponseEntity<Item> scrapAndCreateItem(@RequestBody Map<String, String> request) throws CustomException {
        return new ResponseEntity<>(itemService.scrapAndCreateItem(request.get("url")), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItemByUrl(@RequestBody Map<String, String> request) {
        itemService.deleteItemByUrl(request.get("url"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateCreateItemRequest(Map<String, String> request) throws CustomException {
        if (request.get("url").isEmpty() || request.get("title").isEmpty() || request.get("photo").isEmpty()) {
            throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUpdateItemRequest(Map<String, String> request) throws CustomException {
        if (request.get("id").isEmpty() || request.get("title").isEmpty()) {
            throw new CustomException("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }
}
