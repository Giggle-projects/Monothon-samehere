package com.giggle.samehere;

import com.giggle.samehere.card.exception.CardException;
import com.giggle.samehere.file.exception.FileUploadException;
import com.giggle.samehere.group.exception.GroupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvisor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvisor.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(RuntimeException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse("unexpected exception"));
    }

    @ExceptionHandler(GroupException.class)
    public ResponseEntity<ErrorResponse> handleGroupException(GroupException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(CardException.class)
    public ResponseEntity<ErrorResponse> handleCardDuplicateException(CardException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("선택하신 이메일이 이미 사용 중입니다."));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileException(FileUploadException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진을 업로드하는 도중 오류가 발생했습니다."));
    }
}
