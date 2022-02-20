package com.giggle.samehere.item.dto;

import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemChoices;
import java.util.List;

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
        return new Item(name, new ItemChoices(itemChoices));
    }

    public String getName() {
        return name;
    }

    public List<String> getItemChoices() {
        return itemChoices;
    }
}
