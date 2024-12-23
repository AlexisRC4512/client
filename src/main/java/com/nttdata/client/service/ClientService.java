package com.nttdata.client.service;

import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientResponse> getAllClients();
    Mono<ClientResponse> getClientById(String id);
    Mono<ClientResponse> createClient(ClientRequest clientRequest);
    Mono<ClientResponse> updateClient(String id, ClientRequest clientRequest);
    Mono<Void> deleteClient(String id);
}
