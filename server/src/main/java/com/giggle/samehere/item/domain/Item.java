package com.giggle.samehere.item.domain;

import com.giggle.samehere.card.exception.CardException;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    private ItemChoices itemChoices;

    protected Item() {}

    public Item(String name, ItemChoices itemChoices) {
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public void update(Item other) {
        this.name = other.name;
    }

    public void validateAnswerInChoices(String answer) {
        if(!itemChoices.isAnswerInChoices(answer)) {
            throw new CardException(answer+"는 존재하지 않는 답변입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemChoices getItemChoices() {
        return itemChoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
