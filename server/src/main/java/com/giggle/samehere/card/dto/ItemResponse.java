package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.Item;

public class ItemResponse {

    private final Long id;
    private final String name;

    public ItemResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
