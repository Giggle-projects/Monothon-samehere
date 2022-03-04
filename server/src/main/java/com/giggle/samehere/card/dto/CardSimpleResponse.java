package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.Card;
import java.util.List;
import java.util.stream.Collectors;

public class CardSimpleResponse {

    private Long id;
    private String name;
    private String gender;
    private String imageUrl;

    public CardSimpleResponse(Long id, String name, String gender, String imageUrl) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public static CardSimpleResponse of(Card card) {
        return new CardSimpleResponse(card.getId(), card.getName(), card.getGender(), card.getImagePath());
    }

    public static List<CardSimpleResponse> listOf(List<Card> cardsInGroup) {
        return cardsInGroup.stream().map(CardSimpleResponse::of).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
