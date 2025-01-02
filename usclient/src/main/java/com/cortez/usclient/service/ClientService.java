package com.cortez.usclient.service;

import org.springframework.stereotype.Service;

import com.cortez.usclient.data.Client;
import com.cortez.usclient.repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Mono<Client> getClient(Long codigoUnico) {
        return clientRepository.findByCodigoUnico(codigoUnico);
    }
    
}
