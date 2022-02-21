package com.giggle.samehere.item.dto;

import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemChoices;
import java.util.List;
import java.util.Objects;

public class ItemRequest {

    private String name;
    private List<String> itemChoices;

    public ItemRequest() {
    }

    public ItemRequest(String name, List<String> itemChoices) {
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public Item toItem() {
        if (Objects.isNull(itemChoices) || itemChoices.isEmpty()) {
            return Item.shortQuestion(name);
        }
        return Item.multipleChoicesQuestion(name, new ItemChoices(itemChoices));
    }

    public String getName() {
        return name;
    }

    public List<String> getItemChoices() {
        return itemChoices;
    }
}
