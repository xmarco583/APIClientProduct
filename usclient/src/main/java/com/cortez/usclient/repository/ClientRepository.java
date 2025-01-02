package com.cortez.usclient.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cortez.usclient.data.Client;

import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
    Mono<Client> findByCodigoUnico(Long codigoUnico);
}
