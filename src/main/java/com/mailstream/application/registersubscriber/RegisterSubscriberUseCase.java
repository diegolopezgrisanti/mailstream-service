package com.mailstream.application.registersubscriber;

import com.mailstream.domain.subscriber.Subscriber;
import com.mailstream.domain.subscriber.SubscriberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterSubscriberUseCase {

    private final SubscriberRepository subscriberRepository;

    public RegisterSubscriberUseCase(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Transactional
    public Subscriber registerSubscriber(String email) {
        Optional<Subscriber> existingSubscriber = subscriberRepository.findByEmail(email);
        if (existingSubscriber.isPresent()) {
            throw new IllegalArgumentException("The email is already registered.");
        }

        UUID id = UUID.randomUUID();
        LocalDateTime subscriptionDate = LocalDateTime.now();

        Subscriber newSubscriber = new Subscriber(id, email, subscriptionDate);
        return subscriberRepository.save(newSubscriber);
    }
}