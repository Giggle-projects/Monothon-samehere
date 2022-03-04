package com.giggle.samehere.card.application;

import com.giggle.samehere.card.domain.*;
import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import com.giggle.samehere.card.exception.CardException;
import com.giggle.samehere.group.domain.CardGroup;
import com.giggle.samehere.group.domain.CardGroupRepository;
import com.giggle.samehere.group.domain.Group;
import com.giggle.samehere.group.domain.GroupRepository;
import com.giggle.samehere.group.exception.GroupException;
import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private static final String PROFILE_IMAGE_REQUEST_ROOT_PATH = "/resource";
    private static final String DEFAULT_PROFILE_IMAGE_FILE_NAME = "default.png";

    @Value("${cards.profile.image.upload.folder}")
    private String PHOTO_UPLOAD_FOLDER;

    private final CardRepository cardRepository;
    private final ItemRepository itemRepository;
    private final CardItemRepository cardItemRepository;
    private final GroupRepository groupRepository;
    private final CardGroupRepository cardGroupRepository;

    public CardService(
            CardRepository cardRepository,
            ItemRepository itemRepository,
            CardItemRepository cardItemRepository,
            GroupRepository groupRepository,
            CardGroupRepository cardGroupRepository
    ) {
        this.cardRepository = cardRepository;
        this.itemRepository = itemRepository;
        this.cardItemRepository = cardItemRepository;
        this.groupRepository = groupRepository;
        this.cardGroupRepository = cardGroupRepository;
    }

    @Transactional
    public CardResponse create(CardRequest request, MultipartFile multipartFile) {
        final ImageFile profileImage = ImageFile.save(Paths.get(PHOTO_UPLOAD_FOLDER), multipartFile);
        final Card card = request.toCard(profileImage);
        cardRepository.save(card);
        return cardResponse(card);
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        final Card card = request.toCard(PROFILE_IMAGE_REQUEST_ROOT_PATH + "/" + DEFAULT_PROFILE_IMAGE_FILE_NAME);
        cardRepository.save(card);
        return cardResponse(card);
    }

    @Transactional
    public CardResponse enterInGroup(Long cardId, Long groupId) {
        final Card card = getCard(cardId);
        final Group group = groupRepository.findById(groupId).orElseThrow(IllegalArgumentException::new);
        checkDuplicated(card, group);
        cardGroupRepository.save(new CardGroup(card, group));
        return cardResponse(card);
    }

    private void checkDuplicated(Card card, Group enteringGroup) {
        cardGroupRepository.findAllByCard(card).stream()
                .filter(it -> it.isGroup(enteringGroup))
                .findAny()
                .ifPresent(it -> {
                    throw new GroupException("이미 가입된 카드입니다.");
                });
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = getCard(id);
        card.update(request.toCard(card.getPhotosImagePath()));

        final List<CardItem> cardItems = getRequestCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);
        return cardResponse(card);
    }

    private List<CardItem> getRequestCardItems(CardRequest request, Long cardId) {
        return request.getCardItems().stream()
                .map(it -> new CardItem(cardId, getItem(it.getItemId()), it.getValue()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = getCard(id);
        return cardResponse(card);
    }

    @Transactional(readOnly = true)
    public List<CardSimpleResponse> findAllCardsByGroup(Long groupId) {
        final List<CardGroup> cardGroups = cardGroupRepository.findAllByGroupId(groupId);
        final List<Card> cardsInGroup = cardGroups.stream().map(CardGroup::getCard).collect(Collectors.toList());
        return CardSimpleResponse.listOf(cardsInGroup);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new CardException("존재하지 않는 항목입니다."));
    }

    private Card getCard(Long targetId) {
        return cardRepository.findById(targetId).orElseThrow(() -> new CardException("존재하지 않는 카드입니다."));
    }

    private CardResponse cardResponse(Card card) {
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        final List<Group> groups = cardGroupRepository.findAllByCard(card).stream()
                .map(CardGroup::getGroup)
                .collect(Collectors.toList());
        return CardResponse.of(card, cardItems, groups);
    }
}
