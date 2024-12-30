package com.nttdata.client.util;


import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.request.ClientRequest;
import com.nttdata.client.model.response.ClientResponse;
/**
 * Utility class for converting between Client and ClientRequest/ClientResponse objects.
 */
public class ClientConverter {
    /**
     * Converts a ClientRequest object to a Client object.
     *
     * @param request the ClientRequest object to convert
     * @return the converted Client object
     */
    public static Client toClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setType(request.getType());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setAddress(request.getAddress());
        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());
        client.setSubType(request.getSubTypeClient());
        return client;
    }

    /**
     * Converts a Client object to a ClientResponse object.
     *
     * @param client the Client object to convert
     * @return the converted ClientResponse object
     */
    public static ClientResponse toClientResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setType(client.getType());
        response.setDocumentNumber(client.getDocumentNumber());
        response.setAddress(client.getAddress());
        response.setPhone(client.getPhone());
        response.setEmail(client.getEmail());
        response.setSubTypeClient(client.getSubType());
        return response;
    }
}
