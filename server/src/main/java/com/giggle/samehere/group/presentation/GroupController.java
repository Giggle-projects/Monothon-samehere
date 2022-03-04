package com.giggle.samehere.group.presentation;

import com.giggle.samehere.group.application.GroupService;
import com.giggle.samehere.group.dto.GroupRequest;
import com.giggle.samehere.group.dto.GroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest groupRequest) {
        final GroupResponse response = groupService.create(groupRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAllGroups() {
        final List<GroupResponse> responses = groupService.findAllGroups();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> findGroupById(@PathVariable Long id) {
        final GroupResponse response = groupService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> update(@PathVariable Long id, @RequestBody GroupRequest request) {
        final GroupResponse response = groupService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
