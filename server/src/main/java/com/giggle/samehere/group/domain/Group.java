package com.giggle.samehere.group.domain;

import javax.persistence.*;

@Table(name = "GROUPS")
@Entity
public class Group {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String password;

    public Group() {}

    public Group(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void update(Group other) {
        this.name = other.name;
        this.password = other.password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
