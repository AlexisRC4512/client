package com.service.client.service;

import com.service.client.model.personal_client;
import com.service.client.repository.personalClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalClientService {


    private final personalClientRepository obj_personalClientRepository;

    public PersonalClientService(personalClientRepository objPersonalClientRepository) {
        obj_personalClientRepository = objPersonalClientRepository;
    }

    public List<personal_client> getAllPersonalClients() {
        return obj_personalClientRepository.findAll();
    }

    public personal_client getPersonalClientById(int id) {
        return obj_personalClientRepository.findById(id).orElse(null);
    }

    public personal_client savePersonalClient(personal_client personalClient) {
        return obj_personalClientRepository.save(personalClient);
    }

    public void deletePersonalClient(int id) {
        obj_personalClientRepository.deleteById(id);
    }
}