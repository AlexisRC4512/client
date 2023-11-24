package com.service.client.controller;

import com.service.client.model.business_client;
import com.service.client.service.BusinessClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.web.servlet.function.ServerResponse.status;

@SpringBootTest
public class BusinessClientControllerTest {

    @MockBean
    private BusinessClientService service;

    @Autowired
    private BusinessClientController controller;

    @Test
    public void getAllBusinessClientsTest() {
        controller.getAllBusinessClients();
        verify(service, times(1)).getAllBusinessClients();
    }

    @Test
    public void getBusinessClientByIdTest() {
        int id = 1;
        controller.getBusinessClientById(id);
        verify(service, times(1)).getBusinessClientById(id);
    }

    @Test
    public void saveBusinessClientTest() {
        business_client client = new business_client();
        controller.saveBusinessClient(client);
        verify(service, times(1)).saveBusinessClient(client);
    }

    @Test
    public void deleteBusinessClientTest() {
        int id = 1;
        controller.deleteBusinessClient(id);
        verify(service, times(1)).deleteBusinessClient(id);
    }
}