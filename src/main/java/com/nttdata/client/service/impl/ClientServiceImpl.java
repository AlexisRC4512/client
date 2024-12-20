package com.nttdata.client.service.impl;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.exception.ClientNotFoundException;
import com.nttdata.client.model.exception.InvalidClientDataException;
import com.nttdata.client.model.response.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
import com.nttdata.client.respository.ClientRepository;
import com.nttdata.client.service.ClientService;
import com.nttdata.client.util.ClientConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the ClientService interface.
 * Provides methods for CRUD operations on clients.
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Retrieve all clients.
     *
     * @return a Flux of ClientResponse containing all clients.
     */
    @Override
    public Flux<ClientResponse> getAllClients() {
        log.info("Fetching all clients");
        return clientRepository.findAll()
                .map(ClientConverter::toClientResponse)
                .onErrorMap(e -> new Exception("Error fetching all clients", e));
    }

    /**
     * Retrieve a client by ID.
     *
     * @param id the ID of the client to retrieve.
     * @return a Mono of ClientResponse with the client details.
     */
    @Override
    public Mono<ClientResponse> getClientById(String id) {
        log.debug("Fetching client with id: {}", id);
        return clientRepository.findById(id)
                .map(ClientConverter::toClientResponse)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found with id: " + id)))
                .onErrorMap(e -> new Exception("Error fetching client by id", e));
    }

    /**
     * Create a new client.
     *
     * @param clientRequest the request body containing client details.
     * @return a Mono of ClientResponse with the created client.
     */
    @Override
    public Mono<ClientResponse> createClient(ClientRequest clientRequest) {
        if (clientRequest == null || clientRequest.getName() == null) {
            log.warn("Invalid client data: {}", clientRequest);
            return Mono.error(new InvalidClientDataException("Invalid client data"));
        }
        log.info("Creating new client: {}", clientRequest.getName());
        Client client = ClientConverter.toClient(clientRequest);
        return clientRepository.save(client)
                .map(ClientConverter::toClientResponse)
                .doOnError(e -> log.error("Error creating client", e))
                .onErrorMap(e -> new Exception("Error creating client", e));
    }

    /**
     * Update a client by ID.
     *
     * @param id the ID of the client to update.
     * @param clientRequest the request body containing updated client details.
     * @return a Mono of ClientResponse with the updated client.
     */
    @Override
    public Mono<ClientResponse> updateClient(String id, ClientRequest clientRequest) {
        if (clientRequest == null || clientRequest.getName() == null) {
            log.warn("Invalid client data for update: {}", clientRequest);
            return Mono.error(new InvalidClientDataException("Invalid client data"));
        }
        log.debug("Updating client with id: {}", id);
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found with id: " + id)))
                .flatMap(existingClient -> {
                    Client updatedClient = ClientConverter.toClient(clientRequest);
                    updatedClient.setId(existingClient.getId());
                    return clientRepository.save(updatedClient);
                })
                .map(ClientConverter::toClientResponse)
                .onErrorMap(e -> new Exception("Error updating client", e));
    }

    /**
     * Delete a client by ID.
     *
     * @param id the ID of the client to delete.
     * @return a Mono<Void> indicating completion.
     */
    @Override
    public Mono<Void> deleteClient(String id) {
        log.debug("Deleting client with id: {}", id);
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found with id: " + id)))
                .flatMap(existingClient -> clientRepository.delete(existingClient))
                .onErrorMap(e -> new Exception("Error deleting client", e));
    }
}
