package com.giggle.samehere.card.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.Transient;

@Entity
public class Card {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String profession;
    private String image;
    private String password;

    public Card() {}

    public Card(String name, String email, String gender, String profession, String image, String password) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.image = image;
        this.password = password;
    }

    public void update(Card other) {
        if (this.password.equals(other.password)) {
            this.name = other.name;
            this.email = other.email;
            this.gender = other.gender;
            this.profession = other.profession;
        }
    }

    public Long getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    // TODO :: 저장 경로 수정
    public String getPhotosImagePath() {
        if (image == null || id == null) return null;
        return "/card-photos/" + id + "/" + image;
    }

    public String getPassword() {
        return password;
    }
}
