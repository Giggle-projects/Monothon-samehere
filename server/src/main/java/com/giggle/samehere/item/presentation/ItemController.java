package com.giggle.samehere.item.presentation;

import com.giggle.samehere.item.application.ItemService;
import com.giggle.samehere.item.dto.ItemRequest;
import com.giggle.samehere.item.dto.ItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest itemRequest) {
        final ItemResponse response = itemService.create(itemRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAllItems() {
        final List<ItemResponse> responses = itemService.findAllItems();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findCardById(@PathVariable Long id) {
        final ItemResponse response = itemService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> update(@PathVariable Long id, @RequestBody ItemRequest request) {
        final ItemResponse response = itemService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
