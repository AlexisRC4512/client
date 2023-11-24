package com.service.client.repository;

import com.service.client.model.business_client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface businessClientRepository extends JpaRepository<business_client,Integer> {

}
