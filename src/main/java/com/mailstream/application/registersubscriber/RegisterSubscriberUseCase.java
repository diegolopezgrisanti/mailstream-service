package com.mailstream.application.registersubscriber;

import com.mailstream.domain.subscriber.Subscriber;
import com.mailstream.domain.subscriber.SubscriberRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterSubscriberUseCase {

    private final SubscriberRepository subscriberRepository;

    public RegisterSubscriberUseCase(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Subscriber registerSubscriber(String email) {
        Optional<Subscriber> existingSubscriber = subscriberRepository.findByEmail(email);
        if (existingSubscriber.isPresent()) {
            throw new IllegalArgumentException("Subscriber with this email already exists");
        }

        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setEmail(email);
        return subscriberRepository.save(newSubscriber);
    }
}