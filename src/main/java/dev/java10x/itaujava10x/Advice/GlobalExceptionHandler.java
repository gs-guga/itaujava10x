package dev.java10x.itaujava10x.Advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Void> tratarErroDeConversao(
            HttpMessageNotReadableException exception) {
            log.error("Erro ao ler o corpo da requisição: ", exception);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

        }

        @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> tratarErroDeValidacao(
            IllegalArgumentException exception) {

        log.error("Erro de validação: ", exception);

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .build();
    }
    }


