package com.giggle.samehere.group.domain;

import com.giggle.samehere.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
    List<CardGroup> findAllByCard(Card card);

    List<CardGroup> findAllByGroup(Group group);

    List<CardGroup> findAllByGroupId(Long groupId);
}
