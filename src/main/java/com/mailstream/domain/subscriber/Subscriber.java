package com.mailstream.domain.subscriber;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Subscriber {
    private UUID id;
    private String email;
    private LocalDateTime subscriptionDate;
}