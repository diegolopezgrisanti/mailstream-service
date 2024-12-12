package com.mailstream.domain.subscriber;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subscriber {
    private UUID id;
    private String email;
    private LocalDateTime subscriptionDate;

    public Subscriber(UUID id, String email, LocalDateTime subscriptionDate) {
        this.id = id;
        this.email = email;
        this.subscriptionDate = subscriptionDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}