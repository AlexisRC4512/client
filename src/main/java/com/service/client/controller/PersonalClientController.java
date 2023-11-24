package com.service.client.controller;

import com.service.client.model.personal_client;
import com.service.client.service.PersonalClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal-clients")
public class PersonalClientController {


    private final PersonalClientService personalClientService;
    public PersonalClientController(PersonalClientService personalClientService) {
        this.personalClientService = personalClientService;
    }

    @GetMapping
    public List<personal_client> getAllPersonalClients() {
        return personalClientService.getAllPersonalClients();
    }

    @GetMapping("/{id}")
    public personal_client getPersonalClientById(@PathVariable int id) {
        return personalClientService.getPersonalClientById(id);
    }

    @PostMapping
    public personal_client savePersonalClient(@RequestBody personal_client personalClient) {
        return personalClientService.savePersonalClient(personalClient);
    }

    @DeleteMapping("/{id}")
    public void deletePersonalClient(@PathVariable int id) {
        personalClientService.deletePersonalClient(id);
    }
}
