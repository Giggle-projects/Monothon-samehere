package com.giggle.samehere.item.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
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
    private ItemAnswerType answerType;
    private String name;

    @Column
    @Convert(converter = ItemChoicesConverter.class)
    private List<String> itemChoices = Collections.emptyList();

    protected Item() {}

    private Item(ItemAnswerType answerType, String name, List<String> itemChoices) {
        this.answerType = answerType;
        this.name = name;
        this.itemChoices = itemChoices;
    }

    public static Item shortAnswerQuestion(String name) {
        return new Item(ItemAnswerType.SHORT, name, Collections.emptyList());
    }

    public static Item multipleChoicesQuestion(String name, List<String> itemChoices) {
        return new Item(ItemAnswerType.MULTIPLE, name, itemChoices);
    }

    public void updateName(Item other) {
        this.name = other.name;
    }

    public void validateAnswer(String answer) {
        if(answerType == ItemAnswerType.MULTIPLE) {
            itemChoices.stream()
                    .filter(it -> it.equals(answer))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(answer + "는 존재하지 않는 답변입니다."));
        }
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
        return answerType;
    }
}

