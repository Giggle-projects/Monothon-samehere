package com.giggle.samehere.item.dto;

import com.giggle.samehere.item.domain.Item;
import java.util.List;

public class ItemResponse {

    private final Long id;
    private final String name;
    private final List<String> itemChoices;

    public ItemResponse(Long id, String name, List<String> itemChoices) {
        this.id = id;
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getItemChoices().itemChoices());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getItemChoices() {
        return itemChoices;
    }
}
