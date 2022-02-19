package com.giggle.samehere.card.dto;

public class CardItemRequest {

    private final Long itemId;
    private final String value;

    public CardItemRequest(Long itemId, String value) {
        this.itemId = itemId;
        this.value = value;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getValue() {
        return value;
    }
}
