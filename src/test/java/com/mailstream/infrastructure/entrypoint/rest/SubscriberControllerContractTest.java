package com.mailstream.infrastructure.entrypoint.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailstream.application.registersubscriber.RegisterSubscriberUseCase;
import com.mailstream.domain.subscriber.Subscriber;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;

@WebMvcTest
class SubscriberControllerContractTest {

    @Autowired
    SubscriberController subscriberController;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegisterSubscriberUseCase registerSubscriberUseCase;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    private final String email = "example@mail.com";

    @Test
    void  shouldRegisterSubscriberSuccessfully() {
        //GIVEN
        String requestBody = """
            {
                "email": "example@mail.com"
            }
        """;

        UUID id = UUID.randomUUID();
        LocalDateTime subscriptionDate = LocalDateTime.now();
        Subscriber subscriber = new Subscriber(id, email, subscriptionDate);


        when(registerSubscriberUseCase.registerSubscriber(email))
                .thenReturn(subscriber);

        // WHEN
        MockMvcResponse response = whenARequestToRegisterSubscriberIsReceived(requestBody);

        // THEN
            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                .body("message", containsString("The email has been successfully registered"))
                .body("email", containsString("example@mail.com"))
                .body("subscriptionDate", notNullValue());

        verify(registerSubscriberUseCase, times(1)).registerSubscriber(email);
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() {
        // GIVEN
        String incorrectRequestBody = """
            {
                "email": "notAnEmail"
            }
        """;

        // WHEN
        MockMvcResponse response = whenARequestToRegisterSubscriberIsReceived(incorrectRequestBody);

        // THEN
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("Invalid input"));

        verify(registerSubscriberUseCase, never()).registerSubscriber(email);
    }

    @Test
    void shouldReturn409WhenEmailAlreadyExists() {
        // GIVEN
        String validRequestBody = """
            {
                "email": "example@mail.com"
            }
        """;
        when(registerSubscriberUseCase.registerSubscriber(email))
                .thenThrow(new IllegalArgumentException("The email is already registered."));

        // WHEN
        MockMvcResponse response = whenARequestToRegisterSubscriberIsReceived(validRequestBody);

        // THEN
        response.then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("message", containsString("The email is already registered."));

        verify(registerSubscriberUseCase, times(1)).registerSubscriber(email);
    }

    @Test
    void shouldReturn500WhenUnexpectedErrorOccurs() {
        // GIVEN
        String validRequestBody = """
            {
                "email": "example@mail.com"
            }
        """;

        when(registerSubscriberUseCase.registerSubscriber(email))
                .thenThrow(new RuntimeException("An unexpected error occurred"));

        // WHEN
        MockMvcResponse response = whenARequestToRegisterSubscriberIsReceived(validRequestBody);

        // THEN
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body("message", containsString("An unexpected error occurred"));

        verify(registerSubscriberUseCase, times(1)).registerSubscriber(email);
    }

    private MockMvcResponse whenARequestToRegisterSubscriberIsReceived(String requestBody) {
        return RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/subscribers");
    }
}