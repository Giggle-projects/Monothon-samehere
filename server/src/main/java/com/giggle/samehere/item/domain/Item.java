package com.giggle.samehere.item.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemAnswerType itemAnswerType;
    private String name;

    @Embedded
    private ItemChoices itemChoices;

    protected Item() {}

    private Item(ItemAnswerType itemAnswerType, String name, ItemChoices itemChoices) {
        this.itemAnswerType = itemAnswerType;
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public static Item shortQuestion(String name) {
        return new Item(ItemAnswerType.SHORT, name, ItemChoices.None());
    }

    public static Item multipleChoicesQuestion(String name, ItemChoices itemChoices) {
        return new Item(ItemAnswerType.MULTIPLE, name, itemChoices);
    }

    public void updateName(Item other) {
        this.name = other.name;
    }

    public void validateAnswer(String answer) {
        if (itemAnswerType == ItemAnswerType.MULTIPLE) {
            itemChoices.validateAnswerInChoices(answer);
        }
    }

    public boolean isAnswerType(ItemAnswerType type) {
        return itemAnswerType == type;
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

    public ItemAnswerType getItemType() {
        return itemAnswerType;
    }
}

