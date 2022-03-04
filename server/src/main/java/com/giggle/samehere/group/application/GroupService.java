package com.giggle.samehere.group.application;

import com.giggle.samehere.group.domain.Group;
import com.giggle.samehere.group.domain.GroupRepository;
import com.giggle.samehere.group.dto.GroupRequest;
import com.giggle.samehere.group.dto.GroupResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public GroupResponse create(GroupRequest request) {
        final Group group = request.toEntity();
        groupRepository.save(group);
        return GroupResponse.of(group);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> findAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponse findById(Long id) {
        final Group group = findGroupById(id);
        return GroupResponse.of(group);
    }

    @Transactional
    public GroupResponse update(Long id, GroupRequest request) {
        final Group group = findGroupById(id);
        group.update(request.toEntity());
        return GroupResponse.of(group);
    }

    private Group findGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
