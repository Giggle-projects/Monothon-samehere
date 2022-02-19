package com.giggle.samehere;

import com.giggle.samehere.card.application.CardDuplicateException;
import com.giggle.samehere.file.FileFormatException;
import com.giggle.samehere.file.UploadFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvisor {

    @ExceptionHandler(CardDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleCardDuplicateException(CardDuplicateException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("선택하신 이메일이 이미 사용 중입니다."));
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileException(UploadFileException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진을 업로드하는 도중 오류가 발생했습니다."));
    }

    @ExceptionHandler(FileFormatException.class)
    public ResponseEntity<ErrorResponse> handleFileFormatException(FileFormatException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진 형식은 .png, .jpeg, .jpg 중 하나를 골라주세요."));
    }
}
