package com.service.client.service;
import com.service.client.model.business_client;
import com.service.client.repository.businessClientRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class BusinessClientServiceTest {

    @MockBean
    private businessClientRepository repository;

    @Autowired
    private BusinessClientService service;

    @Test
    public void getAllBusinessClientsTest() {
        service.getAllBusinessClients();
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getBusinessClientByIdTest() {
        int id = 1;
        service.getBusinessClientById(id);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void saveBusinessClientTest() {
        business_client client = new business_client();
        service.saveBusinessClient(client);
        verify(repository, times(1)).save(client);
    }

    @Test
    public void deleteBusinessClientTest() {
        int id = 1;
        service.deleteBusinessClient(id);
        verify(repository, times(1)).deleteById(id);
    }
}