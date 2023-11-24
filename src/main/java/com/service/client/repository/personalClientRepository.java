package com.service.client.repository;

import com.service.client.model.personal_client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface personalClientRepository extends JpaRepository<personal_client,Integer> {
}
