package com.proyecto01.product.service;

import com.proyecto01.product.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {
    private WebClient client = WebClient.create("http://localhost:9091/v1/client");

    public Mono<Client> getClient(String identityNumber){
        return client.get()
                .uri("/document/{identityNumber}", identityNumber).accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Client.class);
    }
}
