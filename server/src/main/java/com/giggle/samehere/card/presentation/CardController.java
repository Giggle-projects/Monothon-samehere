package com.giggle.samehere.card.presentation;

import com.giggle.samehere.card.application.CardService;
import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import com.giggle.samehere.card.exception.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(
            CardRequest request,
            @RequestParam(required = false) MultipartFile multipartFile
    ) {
        final CardResponse response = createCard(request, multipartFile);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<CardResponse> create(
            @PathVariable Long groupId,
            CardRequest request,
            @RequestParam(required = false) MultipartFile multipartFile
    ) {
        final CardResponse savedCard = createCard(request, multipartFile);
        return joinInGroup(savedCard.getId(), groupId);
    }

    private CardResponse createCard(CardRequest request, MultipartFile multipartFile) {
        try {
            return cardService.create(request, multipartFile);
        } catch (FileUploadException e) {
            return cardService.create(request);
        }
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
