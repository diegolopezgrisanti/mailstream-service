package com.mailstream.application.registersubscriber;

import com.mailstream.domain.subscriber.Subscriber;
import com.mailstream.domain.subscriber.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RegisterSubscriberUseCaseTest {

    private final SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
    private final RegisterSubscriberUseCase useCase = new RegisterSubscriberUseCase(subscriberRepository);

    UUID id = UUID.randomUUID();
    String email = "test@example.com";
    LocalDateTime subscriptionDate = LocalDateTime.now();
    Subscriber subscriber = new Subscriber(
            id,
            email,
            subscriptionDate
    );

    @Test
    void shouldRegisterNewSubscriberSuccessfully() {
        // GIVEN
        when(subscriberRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(subscriberRepository.save(Mockito.any(Subscriber.class))).thenReturn(subscriber);

        // WHEN
        Subscriber result = useCase.registerSubscriber(email);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);

        verify(subscriberRepository).findByEmail(email);
        verify(subscriberRepository).save(Mockito.any(Subscriber.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // GIVEN
        when(subscriberRepository.findByEmail(email)).thenReturn(Optional.of(subscriber));

        // WHEN
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.registerSubscriber(email)
        );

        // THEN
        assertEquals("The email is already registered.", exception.getMessage());

        verify(subscriberRepository).findByEmail(email);
        verify(subscriberRepository, never()).save(Mockito.any(Subscriber.class));
    }
}