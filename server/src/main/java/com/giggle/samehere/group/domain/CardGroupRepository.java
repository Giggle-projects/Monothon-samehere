package com.giggle.samehere.group.domain;

import com.giggle.samehere.card.domain.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
    List<CardGroup> findAllByCard(Card card);

    List<CardGroup> findAllByGroupId(Long groupId);
}
