package com.nttdata.client.util;


import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;

public class ClientConverter {

    public static Client toClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setType(request.getType());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setAddress(request.getAddress());
        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());
        return client;
    }


    public static ClientResponse toClientResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setType(client.getType());
        response.setDocumentNumber(client.getDocumentNumber());
        response.setAddress(client.getAddress());
        response.setPhone(client.getPhone());
        response.setEmail(client.getEmail());
        return response;
    }
}
