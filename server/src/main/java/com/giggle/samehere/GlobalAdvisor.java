package com.giggle.samehere;

import com.giggle.samehere.card.exception.CardException;
import com.giggle.samehere.card.exception.FileUploadException;
import com.giggle.samehere.group.exception.GroupException;
import com.giggle.samehere.item.exception.ItemException;
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
        LOGGER.error(String.valueOf(e.getCause()));
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponse("unexpected exception"));
    }

    @ExceptionHandler({GroupException.class, ItemException.class, CardException.class})
    public ResponseEntity<ErrorResponse> handleExpectedDomainException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileException(FileUploadException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("사진을 업로드하는 도중 오류가 발생했습니다."));
    }
}

class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
