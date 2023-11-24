package com.service.client.service;

import com.service.client.model.business_client;
import com.service.client.repository.businessClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessClientService {
    private final businessClientRepository obj_businessClientRepository;

    public BusinessClientService(businessClientRepository obj_businessClientRepository) {
        this.obj_businessClientRepository = obj_businessClientRepository;
    }


    public List<business_client> getAllBusinessClients() {
        return obj_businessClientRepository.findAll();
    }

    public business_client getBusinessClientById(int id) {
        return obj_businessClientRepository.findById(id).orElse(null);
    }

    public business_client saveBusinessClient(business_client businessClient) {
        return obj_businessClientRepository.save(businessClient);
    }

    public void deleteBusinessClient(int id) {
        obj_businessClientRepository.deleteById(id);
    }
}
