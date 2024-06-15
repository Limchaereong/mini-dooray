package com.nhnacademy.accountapi.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleResponseStatusException_shouldReturnResponseEntity() {
        // given
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");

        // when
        ResponseEntity<String> response = globalExceptionHandler.handleResponseStatusException(ex);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Not Found");
    }

    @Test
    void handleException_shouldReturnInternalServerErrorResponseEntity() {
        // given
        Exception ex = new Exception("Some error occurred");

        // when
        ResponseEntity<String> response = globalExceptionHandler.handleException(ex);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("An unexpected error occurred");
    }
}
