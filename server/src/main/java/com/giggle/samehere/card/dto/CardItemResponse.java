package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.CardItem;
import java.util.List;
import java.util.stream.Collectors;

public class CardItemResponse {

    private final String itemName;
    private final String value;

    public CardItemResponse(String itemName, String value) {
        this.itemName = itemName;
        this.value = value;
    }

    public static List<CardItemResponse> listOf(List<CardItem> cardItems) {
        return cardItems.stream().map(CardItemResponse::of).collect(Collectors.toList());
    }

    public static CardItemResponse of(CardItem cardItem) {
        return new CardItemResponse(
                cardItem.getItem().getName(),
                cardItem.getAnswer()
        );
    }

    public String getItemName() {
        return itemName;
    }

    public String getValue() {
        return value;
    }
}
