package com.giggle.samehere.group.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "GROUPS")
@Entity
public class Group {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String password;

    public Group() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
