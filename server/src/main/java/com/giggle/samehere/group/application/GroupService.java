package com.giggle.samehere.group.application;

import com.giggle.samehere.card.domain.Card;
import com.giggle.samehere.card.application.CardDuplicateException;
import com.giggle.samehere.group.domain.GroupRepository;
import com.giggle.samehere.group.dto.GroupRequest;
import com.giggle.samehere.group.dto.GroupResponse;
import com.giggle.samehere.group.domain.CardGroup;
import com.giggle.samehere.group.domain.CardGroupRepository;
import com.giggle.samehere.group.domain.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final CardGroupRepository cardGroupRepository;
    private final GroupRepository groupRepository;

    public GroupService(CardGroupRepository cardGroupRepository, GroupRepository groupRepository) {
        this.cardGroupRepository = cardGroupRepository;
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
        final Group group = getGroup(id);
        return GroupResponse.of(group);
    }

    @Transactional
    public GroupResponse update(Long id, GroupRequest request) {
        final Group group = getGroup(id);
        group.update(request.toEntity());
        return GroupResponse.of(group);
    }

    @Transactional
    public List<GroupResponse> enter(Long groupId, Card card) {
        List<CardGroup> allByCard = cardGroupRepository.findAllByCard(card);
        for (CardGroup cardGroup : allByCard) {
            if (groupId.equals(cardGroup.getGroup().getId())) {
                throw new CardDuplicateException();
            }
        }
        final Group newEntered = groupRepository.findById(groupId).get();
        cardGroupRepository.save(new CardGroup(card, newEntered));
        return findAllByCard(card);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> findAllByCard(Card card) {
        final List<CardGroup> cardGroups = cardGroupRepository.findAllByCard(card);
        final List<Group> groups = cardGroups.stream().map(CardGroup::getGroup).collect(Collectors.toList());
        return GroupResponse.listOf(groups);
    }

    private Group getGroup(Long id) {
        return groupRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
