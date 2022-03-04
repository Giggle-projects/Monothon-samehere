package com.giggle.samehere.group.dto;

import com.giggle.samehere.group.domain.Group;
import java.util.List;
import java.util.stream.Collectors;

public class GroupResponse {

    private final Long id;
    private final String name;

    public GroupResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GroupResponse of(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName()
        );
    }

    public static List<GroupResponse> listOf(List<Group> groups) {
        return groups.stream().map(GroupResponse::of).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
