package com.giggle.samehere.item.dto;

import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemAnswerType;
import java.util.List;

public class ItemResponse {

    private final Long id;
    private final ItemAnswerType itemAnswerType;
    private final String name;
    private final List<String> itemChoices;

    public ItemResponse(Long id, ItemAnswerType itemAnswerType, String name, List<String> itemChoices) {
        this.id = id;
        this.itemAnswerType = itemAnswerType;
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getItemType(), item.getName(), item.getItemChoices());
    }

    public Long getId() {
        return id;
    }

    public ItemAnswerType getItemAnswerType() {
        return itemAnswerType;
    }

    public String getName() {
        return name;
    }

    public List<String> getItemChoices() {
        return itemChoices;
    }
}
