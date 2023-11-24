package com.service.client.controller;

import com.service.client.model.business_client;
import com.service.client.service.BusinessClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-clients")
public class BusinessClientController {

    private final BusinessClientService businessClientService;

    public BusinessClientController(BusinessClientService businessClientService) {
        this.businessClientService = businessClientService;
    }

    @GetMapping
    public List<business_client> getAllBusinessClients() {
        return businessClientService.getAllBusinessClients();
    }

    @GetMapping("/{id}")
    public business_client getBusinessClientById(@PathVariable int id) {
        return businessClientService.getBusinessClientById(id);
    }

    @PostMapping
    public business_client saveBusinessClient(@RequestBody business_client businessClient) {
        return businessClientService.saveBusinessClient(businessClient);
    }

    @DeleteMapping("/{id}")
    public void deleteBusinessClient(@PathVariable int id) {
        businessClientService.deleteBusinessClient(id);
    }
}
