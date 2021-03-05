package io.github.mat3e;

interface DomainEventPublisher {
    void publish(DomainEvent event);
}
