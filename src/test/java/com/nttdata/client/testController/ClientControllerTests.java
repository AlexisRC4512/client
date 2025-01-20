package com.nttdata.client.testController;

import com.nttdata.client.controller.ClientController;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import com.nttdata.client.model.exception.ClientNotFoundException;
import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
import com.nttdata.client.model.response.ErrorResponse;
import com.nttdata.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(ClientController.class)
class ClientControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClientService clientService;

    private ClientResponse client1;
    private ClientResponse client2;
    private ClientRequest clientRequest;
    private ClientResponse clientResponse;

    @BeforeEach
    void setUp() {
         client1 = new ClientResponse();
        client1.setId("1");
        client1.setName("Client1");
        client1.setType(TypeClient.PERSONAL);
        client1.setDocumentNumber(123456);
        client1.setAddress("123 Main St");
        client1.setPhone("555-1234");
        client1.setEmail("client1@example.com");
        client1.setSubTypeClient(SubTypeClient.NORMAL);

         client2 = new ClientResponse();
        client2.setId("2");
        client2.setName("Client2");
        client2.setType(TypeClient.PERSONAL);
        client2.setDocumentNumber(654321);
        client2.setAddress("456 Main St");
        client2.setPhone("555-5678");
        client2.setEmail("client2@example.com");
        client2.setSubTypeClient(SubTypeClient.NORMAL);
        clientRequest = new ClientRequest();
        clientRequest.setName("Client1");
        clientRequest.setType(TypeClient.PERSONAL);
        clientRequest.setDocumentNumber(123456);
        clientRequest.setAddress("123 Main St");
        clientRequest.setPhone("555-1234");
        clientRequest.setEmail("client1@example.com");
        clientRequest.setSubTypeClient(SubTypeClient.NORMAL);

        clientResponse = new ClientResponse("1", "Client1", TypeClient.PERSONAL, 123456, "123 Main St", "555-1234", "client1@example.com", SubTypeClient.NORMAL);
    }
    @Test
     void testGettersAndSetters() {
        assertEquals("1", client1.getId());
        assertEquals("Client1", client1.getName());
        assertEquals(TypeClient.PERSONAL, client1.getType());
        assertEquals(123456, client1.getDocumentNumber());
        assertEquals("123 Main St", client1.getAddress());
        assertEquals("555-1234", client1.getPhone());
        assertEquals("client1@example.com", client1.getEmail());
        assertEquals(SubTypeClient.NORMAL, client1.getSubTypeClient());
    }

    @Test
    void testGetAllClients() {
        given(clientService.getAllClients()).willReturn(Flux.just(client1, client2));
        webTestClient.get().uri("/api/v1/client")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(ClientResponse.class)
                .hasSize(2)
                .contains(client1, client2);
    }

    @Test
    void testCreateClient() {

        given(clientService.createClient(clientRequest)).willReturn(Mono.just(clientResponse));

        webTestClient.post().uri("/api/v1/client")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(clientResponse),ClientResponse.class)
                .exchange()
                .expectStatus().isOk();

    }



    @Test
    void testGetClientById() {
        String clientId = "1";
        given(clientService.getClientById(clientId)).willReturn(Mono.just(clientResponse));

        webTestClient.get().uri("/api/v1/client/{id}", clientId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(ClientResponse.class)
                .isEqualTo(clientResponse);
    }

    @Test
    void testUpdateClient() {
        String clientId = "1";
        ClientRequest updatedClientRequest = new ClientRequest();
        updatedClientRequest.setName("UpdatedClient");
        updatedClientRequest.setType(TypeClient.PERSONAL);
        updatedClientRequest.setDocumentNumber(123456);
        updatedClientRequest.setAddress("123 Main St");
        updatedClientRequest.setPhone("555-1234");
        updatedClientRequest.setEmail("updatedclient@example.com");
        updatedClientRequest.setSubTypeClient(SubTypeClient.NORMAL);

        ClientResponse updatedClientResponse = new ClientResponse(clientId, "UpdatedClient", TypeClient.PERSONAL, 123456, "123 Main St", "555-1234", "updatedclient@example.com", SubTypeClient.NORMAL);
        given(clientService.updateClient(clientId, updatedClientRequest)).willReturn(Mono.just(updatedClientResponse));

        webTestClient.put().uri("/api/v1/client/{id}", clientId)
                .contentType(APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(clientResponse),ClientResponse.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteClient() {
        String clientId = "1";
        given(clientService.deleteClient(clientId)).willReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/client/{id}", clientId)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void testHandleClientNotFoundException() {
        given(clientService.getClientById("1")).willThrow(new ClientNotFoundException("Client not found"));

        webTestClient.get().uri("/api/v1/client/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorResponse.class)
                .value(errorResponse -> {
                    assertThat(errorResponse.getError()).isEqualTo("Client Not Found");
                    assertThat(errorResponse.getMessage()).isEqualTo("Client not found");
                });
    }


    @Test
    void testHandleGenericException() {
        given(clientService.getClientById("1")).willThrow(new RuntimeException("Generic error"));

        webTestClient.get().uri("/api/v1/client/1")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(ErrorResponse.class)
                .value(errorResponse -> {
                    assertThat(errorResponse.getError()).isEqualTo("Internal Server Error");
                    assertThat(errorResponse.getMessage()).isEqualTo("Generic error");
                });
    }

}
