package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.Item;

public class ItemRequest {

    private String name;

    public ItemRequest() {

    }

    public ItemRequest(String name) {
        this.name = name;
    }

    public Item toItem() {
        return new Item(name);
    }

    public String getName() {
        return name;
    }
}
