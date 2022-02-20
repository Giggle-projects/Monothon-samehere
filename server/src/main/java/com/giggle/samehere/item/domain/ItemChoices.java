package com.giggle.samehere.item.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class ItemChoices {

    @Transient
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String itemChoicesAsData = "";

    public ItemChoices() {
    }

    public ItemChoices(List<String> itemChoiceNames) {
        try {
            this.itemChoicesAsData = OBJECT_MAPPER.writeValueAsString(itemChoiceNames);
        } catch (JsonProcessingException jpe) {
            throw new IllegalArgumentException(jpe.getMessage());
        }
    }

    public ItemChoices(String... itemChoiceNames) {
        this(Arrays.asList(itemChoiceNames));
    }

    public List<String> itemChoices() {
        try {
            if (itemChoicesAsData.isEmpty()) {
                return Collections.emptyList();
            }
            return OBJECT_MAPPER.readValue(itemChoicesAsData, List.class);
        } catch (JsonProcessingException jpe) {
            throw new IllegalArgumentException(jpe.getMessage());
        }
    }

    public boolean isAnswerInChoices(String answer) {
        if(answer.isEmpty()) {
            return true;
        }
        return itemChoices().stream().anyMatch(it -> it.equals(answer));
    }
}
