package com.giggle.samehere.card.application;

import java.util.List;
import java.util.stream.Collectors;

import com.giggle.samehere.card.domain.Item;
import com.giggle.samehere.card.domain.ItemRepository;
import com.giggle.samehere.card.dto.ItemRequest;
import com.giggle.samehere.card.dto.ItemResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemResponse create(ItemRequest request) {
        final Item item = request.toItem();
        itemRepository.save(item);
        return ItemResponse.of(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findAllItems() {
        return itemRepository.findAll().stream()
            .map(ItemResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponse findById(Long id) {
        final Item item = itemRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return ItemResponse.of(item);
    }

    @Transactional
    public ItemResponse update(Long id, ItemRequest request) {
        final Item item = itemRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        item.update(request.toItem());
        return ItemResponse.of(item);
    }
}
