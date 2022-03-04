package com.giggle.samehere.card.dto;

import com.giggle.samehere.card.domain.Card;
import com.giggle.samehere.card.domain.ImageFile;
import java.util.List;

public class CardRequest {

    private String name;
    private String email;
    private String gender;
    private String profession;
    private String password;
    private List<CardItemRequest> cardItems;

    public CardRequest() {
    }

    public CardRequest(String name,
                       String email,
                       String gender,
                       String profession,
                       String password,
                       List<CardItemRequest> cardItems) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.password = password;
        this.cardItems = cardItems;
    }

    public Card toCard(String imageName) {
        return new Card(name, email, gender, profession, imageName, password);
    }

    public Card toCard(ImageFile imageFile) {
        return new Card(name, email, gender, profession, imageFile.path().toString(), password);
    }

    public String getName() {
        return name;
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

    public List<CardItemRequest> getCardItems() {
        return cardItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCardItems(List<CardItemRequest> cardItems) {
        this.cardItems = cardItems;
    }
}
