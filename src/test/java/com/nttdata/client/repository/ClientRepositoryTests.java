package com.nttdata.client.repository;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import com.nttdata.client.respository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

     Client client1;
     Client client2;

    @BeforeEach
    void setUp() {
        client1 = new Client("1", "Client1", TypeClient.PERSONAL, 123456, "123 Main St", "555-1234", "client1@example.com", SubTypeClient.NORMAL);
        client2 = new Client("2", "Client2", TypeClient.PERSONAL, 654321, "456 Main St", "555-5678", "client2@example.com", SubTypeClient.NORMAL);
    }

    @Test
    void testSaveClient() {
        Mono<Client> savedClient = clientRepository.save(client1);
        StepVerifier.create(savedClient)
                .expectNextMatches(client -> client.getId().equals("1") && client.getName().equals("Client1"))
                .verifyComplete();
    }

    @Test
    void testFindClientById() {
        clientRepository.save(client1).block();
        Mono<Client> foundClient = clientRepository.findById("1");
        StepVerifier.create(foundClient)
                .expectNextMatches(client -> client.getId().equals("1") && client.getName().equals("Client1"))
                .verifyComplete();
    }


    @Test
    void testDeleteClient() {
        clientRepository.save(client1).block();
        Mono<Void> deletedClient = clientRepository.delete(client1);
        StepVerifier.create(deletedClient)
                .verifyComplete();
        StepVerifier.create(clientRepository.findById("1"))
                .expectNextCount(0)
                .verifyComplete();
    }
}