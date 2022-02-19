package com.giggle.samehere.card.domain;

import javax.persistence.*;

@Entity
public class CardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardId;

    @ManyToOne
    private Item item;
    private String value;

    protected CardItem() {}

    public CardItem(Long cardId, Item item, String value) {
        this.cardId = cardId;
        this.item = item;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getCardId() {
        return cardId;
    }

    public Item getItem() {
        return item;
    }

    public String getValue() {
        return value;
    }
}
