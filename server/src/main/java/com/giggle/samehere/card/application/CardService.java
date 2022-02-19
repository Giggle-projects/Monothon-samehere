package com.giggle.samehere.card.application;

import com.giggle.samehere.card.domain.Card;
import com.giggle.samehere.card.domain.CardItem;
import com.giggle.samehere.card.domain.CardItemRepository;
import com.giggle.samehere.card.domain.CardRepository;
import com.giggle.samehere.card.domain.Item;
import com.giggle.samehere.card.domain.ItemRepository;
import com.giggle.samehere.card.dto.CardItemRequest;
import com.giggle.samehere.card.dto.CardItemResponse;
import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import com.giggle.samehere.card.exception.CardException;
import com.giggle.samehere.group.application.GroupService;
import com.giggle.samehere.group.domain.CardGroup;
import com.giggle.samehere.group.domain.CardGroupRepository;
import com.giggle.samehere.group.dto.GroupResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private final GroupService groupService;
    private final CardRepository cardRepository;
    private final CardItemRepository cardItemRepository;
    private final CardGroupRepository cardGroupRepository;
    private final ItemRepository itemRepository;

    public CardService(
            GroupService groupService,
            CardRepository cardRepository,
            CardItemRepository cardItemRepository,
            CardGroupRepository cardGroupRepository,
            ItemRepository itemRepository
    ) {
        this.groupService = groupService;
        this.cardRepository = cardRepository;
        this.cardItemRepository = cardItemRepository;
        this.cardGroupRepository = cardGroupRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public CardResponse create(CardRequest request, String imageFile) {
        final Card card = request.toCard(imageFile);
        cardRepository.save(card);
        return CardResponse.of(card, Collections.emptyList(), Collections.emptyList());
    }

    @Transactional
    public CardResponse createInGroup(Long groupId, CardRequest request, String imageFile) {
        final Card card = cardRepository.save(request.toCard(imageFile));
        final List<GroupResponse> groupResponses = groupService.enter(groupId, card);
        return CardResponse.of(card, Collections.emptyList(), groupResponses);
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = getCard(id);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groups);
    }

    @Transactional(readOnly = true)
    public CardResponse findWithComparing(Long targetId, Long myId) {
        final Card target = getCard(targetId);
        final Card mine = getCard(myId);

        final List<CardItem> targetItems = cardItemRepository.findAllByCardId(target.getId());
        final List<CardItem> myItems = cardItemRepository.findAllByCardId(mine.getId());

        final List<CardItemResponse> responses = targetItems.stream()
                .map(it -> compareWithMine(it, myItems))
                .collect(Collectors.toList());

        return CardResponse.of(target, responses, groupService.findAllByCard(target));
    }

    private CardItemResponse compareWithMine(CardItem targetItem, List<CardItem> myItems) {
        if (isIncluded(targetItem, myItems)) {
            return CardItemResponse.sameHere(targetItem);
        }
        return CardItemResponse.of(targetItem);
    }

    private boolean isIncluded(CardItem targetItem, List<CardItem> myItems) {
        return myItems.stream().anyMatch(targetItem::isSame);
    }

    @Transactional(readOnly = true)
    public List<CardSimpleResponse> findAllCardsByGroup(Long groupId) {
        final List<CardGroup> cardGroups = cardGroupRepository.findAllByGroupId(groupId);
        final List<Card> cardsInGroup = cardGroups.stream().map(CardGroup::getCard).collect(Collectors.toList());
        return CardSimpleResponse.listOf(cardsInGroup);
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = getCard(id);
        card.update(request.toCard(card.getPhotosImagePath()));

        final List<CardItem> cardItems = getRequestCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);

        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groups);
    }

    private List<CardItem> getRequestCardItems(CardRequest request, Long cardId) {
        if (Objects.isNull(request.getCardItems())) {
            return Collections.emptyList();
        }
        return request.getCardItems().stream()
                .map(it -> new CardItem(cardId, getItem(it), it.getValue()))
                .collect(Collectors.toList());
    }

    public CardResponse enterInGroup(Long cardId, Long groupId) {
        final Card card = getCard(cardId);
        final List<GroupResponse> groupResponses = groupService.enter(groupId, card);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groupResponses);
    }

    private Item getItem(CardItemRequest it) {
        return itemRepository.findById(it.getItemId()).orElseThrow(() -> new CardException("존재하지 않는 항목입니다."));
    }

    private Card getCard(Long targetId) {
        return cardRepository.findById(targetId).orElseThrow(() -> new CardException("존재하지 않는 카드입니다."));
    }
}
