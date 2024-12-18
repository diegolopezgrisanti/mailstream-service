package com.mailstream.domain.subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberRepository  extends JpaRepository<Subscriber, UUID>{
    Optional<Subscriber> findByEmail(String email);
}