package com.giggle.samehere.card.presentation;

import com.giggle.samehere.card.dto.CardRequest;
import com.giggle.samehere.card.dto.CardResponse;
import com.giggle.samehere.card.dto.CardSimpleResponse;
import com.giggle.samehere.card.application.CardService;
import com.giggle.samehere.file.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;
    private final FileService fileService;

    public CardController(CardService cardService, FileService fileService) {
        this.cardService = cardService;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(CardRequest request, @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        final String fileName = fileService.saveImageFile(multipartFile);
        final CardResponse response = cardService.create(request, fileName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<CardResponse> createInGroup(@PathVariable Long groupId, CardRequest cardRequest, @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        final String imageName = fileService.saveImageFile(multipartFile);
        final CardResponse response = cardService.createInGroup(groupId, cardRequest, imageName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cardId}/groups/{groupId}")
    public ResponseEntity<CardResponse> enter(@PathVariable Long cardId, @PathVariable Long groupId) {
        final CardResponse response = cardService.enterInGroup(cardId, groupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<CardSimpleResponse>> cardsInGroup(@PathVariable Long groupId) {
        final List<CardSimpleResponse> responses = cardService.findAllCardsByGroup(groupId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> findCardById(@PathVariable Long cardId, Long myId) {
        System.out.println(">>>>>>>>>>>>>>>>>" + myId);
        if (Objects.isNull(myId)) {
            final CardResponse response = cardService.findById(cardId);
            return ResponseEntity.ok(response);
        }
        final CardResponse response = cardService.findWithComparing(cardId, myId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable Long id, @RequestBody CardRequest request) {
        final CardResponse response = cardService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
