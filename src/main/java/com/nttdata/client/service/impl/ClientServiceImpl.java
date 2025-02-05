package com.nttdata.client.service.impl;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.exception.ClientNotFoundException;
import com.nttdata.client.model.exception.InvalidClientDataException;
import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
import com.nttdata.client.respository.ClientRepository;
import com.nttdata.client.service.ClientService;
import com.nttdata.client.util.ClientConverter;
import com.nttdata.client.util.ClientValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nttdata.client.util.constans.ConstantsMessage.CLIENT_NOT_FOUND;


/**
 * Implementation of the ClientService interface.
 * Provides methods for CRUD operations on clients.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {


    private final ClientRepository clientRepository;

    /**
     * Retrieve all clients.
     *
     * @return a Flux of ClientResponse containing all clients.
     */
    @CircuitBreaker(name = "client", fallbackMethod = "fallbackGetAllClients")
    @TimeLimiter(name = "client")
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
    @CircuitBreaker(name = "client", fallbackMethod = "fallbackGetClientById")
    @TimeLimiter(name = "client")
    @Override
    public Mono<ClientResponse> getClientById(String id) {
        log.debug("Fetching client with id: {}", id);
        return clientRepository.findById(id)
                .map(ClientConverter::toClientResponse)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(CLIENT_NOT_FOUND + id)))
                .doOnError(e -> log.error("Error fetching client with id: {}", id, e))
                .onErrorMap(e -> new Exception("Error fetching client by id", e));
    }

    /**
     * Create a new client.
     *
     * @param clientRequest the request body containing client details.
     * @return a Mono of ClientResponse with the created client.
     */
    @CircuitBreaker(name = "client", fallbackMethod = "fallbackCreateClient")
    @TimeLimiter(name = "client")
    @Override
    public Mono<ClientResponse> createClient(ClientRequest clientRequest) {
        if (clientRequest == null || clientRequest.getName() == null) {
            log.warn("Invalid client data: {}", clientRequest);
            return Mono.error(new InvalidClientDataException("Invalid client data"));
        }
        if (!ClientValidator.isValidSubtipo(clientRequest.getType(), clientRequest.getSubTypeClient())) {
            throw new IllegalArgumentException("The subtype " + clientRequest.getType() + " is not valid for the type " + clientRequest.getSubTypeClient());
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
    @CircuitBreaker(name = "client", fallbackMethod = "fallbackUpdateClient")
    @TimeLimiter(name = "client")
    @Override
    public Mono<ClientResponse> updateClient(String id, ClientRequest clientRequest) {
        if (clientRequest == null || clientRequest.getName() == null) {
            log.warn("Invalid client data for update: {}", clientRequest);
            return Mono.error(new InvalidClientDataException("Invalid client data"));
        }
        log.debug("Updating client with id: {}", id);
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(CLIENT_NOT_FOUND + id)))
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
    @CircuitBreaker(name = "client", fallbackMethod = "fallbackDeleteClient")
    @TimeLimiter(name = "client")
    @Override
    public Mono<Void> deleteClient(String id) {
        log.debug("Deleting client with id: {}", id);
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(CLIENT_NOT_FOUND + id)))
                .flatMap(existingClient -> clientRepository.delete(existingClient))
                .onErrorMap(e -> new Exception("Error deleting client", e));
    }



    public Flux<ClientResponse> fallbackGetAllClients(Exception exception) {
        log.error("Fallback method for getAllClients", exception);
        return Flux.error(new Exception("Fallback method for getAllClients"));
    }

    public Mono<ClientResponse> fallbackGetClientById(Exception exception) {
        log.error("Fallback method for getClientById", exception);
        return Mono.error(new Exception("Fallback method for getClientById"));
    }

    public Mono<ClientResponse> fallbackCreateClient(Exception exception) {
        log.error("Fallback method for createClient", exception);
        return Mono.error(new Exception("Fallback method for createClient"));
    }

    public Mono<ClientResponse> fallbackUpdateClient(Exception exception) {
        log.error("Fallback method for updateClient", exception);
        return Mono.error(new Exception("Fallback method for updateClient"));
    }

    public Mono<Void> fallbackDeleteClient(Exception exception) {
        log.error("Fallback method for deleteClient", exception);
        return Mono.error(new Exception("Fallback method for deleteClient"));
    }
}
