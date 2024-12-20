package com.nttdata.client.testService;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.enums.TypeClient;
import com.nttdata.client.model.exception.InvalidClientDataException;
import com.nttdata.client.model.response.ClientRequest;
import com.nttdata.client.respository.ClientRepository;
import com.nttdata.client.service.impl.ClientServiceImpl;
import com.nttdata.client.util.ClientConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestService {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllClients() {
        Client client1 = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");
        Client client2 = new Client("2", "Client2", TypeClient.BUSINESS, 654321, "Address2", "0987654321", "client2@example.com");

        when(clientRepository.findAll()).thenReturn(Flux.just(client1, client2));

        StepVerifier.create(clientService.getAllClients())
                .expectNext(ClientConverter.toClientResponse(client1))
                .expectNext(ClientConverter.toClientResponse(client2))
                .verifyComplete();
    }

    @Test
    public void testGetAllClients_Error() {
        when(clientRepository.findAll()).thenReturn(Flux.error(new RuntimeException("Database error")));

        StepVerifier.create(clientService.getAllClients())
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error fetching all clients"))
                .verify();
    }

    @Test
    public void testGetClientById() {
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");

        when(clientRepository.findById("1")).thenReturn(Mono.just(client));

        StepVerifier.create(clientService.getClientById("1"))
                .expectNext(ClientConverter.toClientResponse(client))
                .verifyComplete();
    }

    @Test
    public void testGetClientById_NotFound() {
        when(clientRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(clientService.getClientById("1"))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error fetching client by id"))
                .verify();
    }

    @Test
    public void testCreateClient() {
        ClientRequest clientRequest = new ClientRequest("Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));

        StepVerifier.create(clientService.createClient(clientRequest))
                .expectNext(ClientConverter.toClientResponse(client))
                .verifyComplete();
    }

    @Test
    public void testCreateClient_InvalidData() {
        ClientRequest clientRequest = new ClientRequest(null, TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");

        StepVerifier.create(clientService.createClient(clientRequest))
                .expectErrorMatches(throwable -> throwable instanceof InvalidClientDataException &&
                        throwable.getMessage().equals("Invalid client data"))
                .verify();
    }

    @Test
    public void testUpdateClient() {
        ClientRequest clientRequest = new ClientRequest("UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com");
        Client existingClient = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");
        Client updatedClient = new Client("1", "UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com");

        when(clientRepository.findById("1")).thenReturn(Mono.just(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(updatedClient));

        StepVerifier.create(clientService.updateClient("1", clientRequest))
                .expectNext(ClientConverter.toClientResponse(updatedClient))
                .verifyComplete();
    }

    @Test
    public void testUpdateClient_NotFound() {
        ClientRequest clientRequest = new ClientRequest("UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com");

        when(clientRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(clientService.updateClient("1", clientRequest))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error updating client"))
                .verify();
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com");

        when(clientRepository.findById("1")).thenReturn(Mono.just(client));
        when(clientRepository.delete(client)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClient("1"))
                .verifyComplete();
    }

    @Test
    public void testDeleteClient_NotFound() {
        when(clientRepository.findById("1")).thenReturn(Mono.empty());
        StepVerifier.create(clientService.deleteClient("1"))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error deleting client"))
                .verify();
    }
}
