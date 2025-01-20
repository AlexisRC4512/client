package com.nttdata.client.testService;

import com.nttdata.client.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class ClientServiceFallbackTests {

    @Autowired
    private ClientServiceImpl clientService;

    @Test
    void testFallbackGetAllClients() {
        StepVerifier.create(clientService.fallbackGetAllClients(new RuntimeException("Service unavailable")))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Fallback method for getAllClients"))
                .verify();
    }

    @Test
    void testFallbackGetClientById() {
        StepVerifier.create(clientService.fallbackGetClientById(new RuntimeException("Service unavailable")))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Fallback method for getClientById"))
                .verify();
    }

    @Test
    void testFallbackCreateClient() {
        StepVerifier.create(clientService.fallbackCreateClient(new RuntimeException("Service unavailable")))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Fallback method for createClient"))
                .verify();
    }

    @Test
    void testFallbackUpdateClient() {
        StepVerifier.create(clientService.fallbackUpdateClient(new RuntimeException("Service unavailable")))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Fallback method for updateClient"))
                .verify();
    }

    @Test
    void testFallbackDeleteClient() {
        StepVerifier.create(clientService.fallbackDeleteClient(new RuntimeException("Service unavailable")))
                .expectErrorMatches(throwable -> throwable instanceof Exception &&
                        throwable.getMessage().equals("Fallback method for deleteClient"))
                .verify();
    }
}