package com.giggle.samehere.card.application;

import com.giggle.samehere.card.domain.Card;
import com.giggle.samehere.card.domain.CardItem;
import com.giggle.samehere.card.domain.CardItemRepository;
import com.giggle.samehere.card.domain.CardRepository;
import com.giggle.samehere.card.domain.ImageFile;
import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import com.giggle.samehere.card.exception.CardException;
import com.giggle.samehere.card.exception.FileUploadException;
import com.giggle.samehere.group.domain.CardGroup;
import com.giggle.samehere.group.domain.CardGroupRepository;
import com.giggle.samehere.group.domain.Group;
import com.giggle.samehere.group.domain.GroupRepository;
import com.giggle.samehere.group.exception.GroupException;
import com.giggle.samehere.item.domain.Item;
import com.giggle.samehere.item.domain.ItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CardService {

    @Value("${cards.profile.image.upload.folder}")
    private String IMAGE_FOLDER_PATH;

    @Value("${cards.profile.image.default.name}")
    private String DEFAULT_PROFILE_IMAGE_FILE_NAME;

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
        try {
            final ImageFile imageFile = ImageFile.save(IMAGE_FOLDER_PATH, multipartFile);
            final Card card = request.toCard(imageFile);
            cardRepository.save(card);
            return cardResponse(card);
        } catch (FileUploadException fue) {
            fue.printStackTrace();
            return create(request);
        }
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        final ImageFile imageFile = ImageFile.pathOf(IMAGE_FOLDER_PATH + DEFAULT_PROFILE_IMAGE_FILE_NAME);
        final Card card = request.toCard(imageFile);
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
        final Card oldCard = getCard(id);
        final Card newCard = request.toCard(oldCard.getImagePath());
        oldCard.update(newCard);

        final List<CardItem> cardItems = getRequestCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);
        return cardResponse(oldCard);
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
