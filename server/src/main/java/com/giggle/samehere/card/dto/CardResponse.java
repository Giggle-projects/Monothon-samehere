package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.Card;
import com.giggle.samehere.card.domain.CardItem;
import com.giggle.samehere.group.domain.Group;
import com.giggle.samehere.group.dto.GroupResponse;
import java.util.List;

public class CardResponse {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private String profession;
    private String imageUrl;
    private List<GroupResponse> groups;
    private List<CardItemResponse> cardItems;

    public CardResponse(Long id,
                        String name,
                        String email,
                        String gender,
                        String profession,
                        String imageUrl,
                        List<GroupResponse> groups,
                        List<CardItemResponse> cardItems) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.imageUrl = imageUrl;
        this.groups = groups;
        this.cardItems = cardItems;
    }

    public static CardResponse of(Card card, List<CardItem> cardItems, List<Group> groups) {
        return new CardResponse(
                card.getId(),
                card.getName(),
                card.getEmail(),
                card.getGender(),
                card.getProfession(),
                card.getImagePath(),
                GroupResponse.listOf(groups),
                CardItemResponse.listOf(cardItems)
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getProfession() {
        return profession;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<GroupResponse> getGroups() {
        return groups;
    }

    public List<CardItemResponse> getCardItems() {
        return cardItems;
    }
}
