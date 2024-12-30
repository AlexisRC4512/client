package com.nttdata.client.testService;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.respository.ClientRepository;
import com.nttdata.client.service.impl.ClientServiceImpl;
import com.nttdata.client.util.ClientConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class TestService {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetAllClients() {
        Client client1 = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com", SubTypeClient.NORMAL);
        Client client2 = new Client("2", "Client2", TypeClient.BUSINESS, 654321, "Address2", "0987654321", "client2@example.com", SubTypeClient.NORMAL);

        when(clientRepository.findAll()).thenReturn(Flux.just(client1, client2));

        StepVerifier.create(clientService.getAllClients())
                .expectNext(ClientConverter.toClientResponse(client1))
                .expectNext(ClientConverter.toClientResponse(client2))
                .verifyComplete();
    }

    @Test
    public void testGetAllClientsError() {
        when(clientRepository.findAll()).thenReturn(Flux.error(new RuntimeException("Database error")));
        StepVerifier.create(clientService.getAllClients())
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error fetching all clients"))
                .verify();
    }

    @Test
    public void testGetClientById() {
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com",SubTypeClient.NORMAL);

        when(clientRepository.findById("1")).thenReturn(Mono.just(client));
        StepVerifier.create(clientService.getClientById("1"))
                .expectNext(ClientConverter.toClientResponse(client))
                .verifyComplete();
    }

    @Test
    public void testGetClientByIdNotFound() {
        when(clientRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(clientService.getClientById("1"))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error fetching client by id"))
                .verify();
    }

    @Test
    public void testCreateClient() {
        ClientRequest clientRequest = new ClientRequest("Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com",SubTypeClient.NORMAL);
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com",SubTypeClient.NORMAL);

        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(client));
        StepVerifier.create(clientService.createClient(clientRequest))
                .expectNext(ClientConverter.toClientResponse(client))
                .verifyComplete();
    }


    @Test
    public void testUpdateClient() {
        ClientRequest clientRequest = new ClientRequest("UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com",SubTypeClient.NORMAL);
        Client existingClient = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com",SubTypeClient.NORMAL);
        Client updatedClient = new Client("1", "UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com",SubTypeClient.NORMAL);

        when(clientRepository.findById("1")).thenReturn(Mono.just(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(updatedClient));
        StepVerifier.create(clientService.updateClient("1", clientRequest))
                .expectNext(ClientConverter.toClientResponse(updatedClient))
                .verifyComplete();
    }

    @Test
    public void testUpdateClientNotFound() {
        ClientRequest clientRequest = new ClientRequest("UpdatedClient", TypeClient.BUSINESS, 654321, "UpdatedAddress", "0987654321", "updatedclient@example.com",SubTypeClient.NORMAL);

        when(clientRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(clientService.updateClient("1", clientRequest))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error updating client"))
                .verify();
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "Address1", "1234567890", "client1@example.com",SubTypeClient.NORMAL);

        when(clientRepository.findById("1")).thenReturn(Mono.just(client));
        when(clientRepository.delete(client)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClient("1"))
                .verifyComplete();
    }

    @Test
    public void testDeleteClientNotFound() {
        when(clientRepository.findById("1")).thenReturn(Mono.empty());
        StepVerifier.create(clientService.deleteClient("1"))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Error deleting client"))
                .verify();
    }
}
