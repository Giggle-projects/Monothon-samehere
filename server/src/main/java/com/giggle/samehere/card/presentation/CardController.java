package com.giggle.samehere.card.presentation;

import com.giggle.samehere.card.application.CardService;
import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(
            @ModelAttribute CardRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        final CardResponse response = createCard(request, image);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<CardResponse> create(
            @PathVariable Long groupId,
            @ModelAttribute CardRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        final CardResponse savedCard = createCard(request, image);
        return joinInGroup(savedCard.getId(), groupId);
    }

    private CardResponse createCard(CardRequest request, MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            return cardService.create(request);
        }
        return cardService.create(request, multipartFile);
    }

    @PostMapping("/{cardId}/groups/{groupId}")
    public ResponseEntity<CardResponse> joinInGroup(@PathVariable Long cardId, @PathVariable Long groupId) {
        final CardResponse response = cardService.enterInGroup(cardId, groupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<CardSimpleResponse>> cardsInGroup(@PathVariable Long groupId) {
        final List<CardSimpleResponse> responses = cardService.findAllCardsByGroup(groupId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> findCardById(@PathVariable Long cardId) {
        final CardResponse response = cardService.findById(cardId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable Long id, @RequestBody CardRequest request) {
        final CardResponse response = cardService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
