package com.nttdata.client.controller;

import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
import com.nttdata.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public Flux<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    public Mono<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @GetMapping("/{id}")
    public Mono<ClientResponse> getClientById(@PathVariable String id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    public Mono<ClientResponse> updateClient(@PathVariable String id, @RequestBody ClientRequest clientRequest) {
        return clientService.updateClient(id, clientRequest);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteClient(@PathVariable String id) {
        return clientService.deleteClient(id);
    }
}