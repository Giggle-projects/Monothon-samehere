package com.giggle.samehere.card.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardItemRepository extends JpaRepository<CardItem, Long> {

    List<CardItem> findAllByCardId(Long cardId);

    void deleteAllByCardId(Long cardId);
}
