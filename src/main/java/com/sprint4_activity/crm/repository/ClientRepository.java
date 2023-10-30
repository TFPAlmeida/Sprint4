package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

    Client findClientById(long id);
}
