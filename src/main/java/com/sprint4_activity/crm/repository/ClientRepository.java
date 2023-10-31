package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

    Client findClientById(long id);

    //named parameters
    @Query("From Client WHERE id>=:min and id<=:max")
    List<Client> getClientByIdRange(long min, long max);

    //positional parameters
    @Query("FROM Client WHERE id >= ?1 AND id <= ?2")
    List<Client> findClientByIdRange(long min, long max);

    @Query(value = "Select ORDERS_ID FROM CLIENTS_TBL_ORDERS where CLIENT_ID =:id", nativeQuery = true)
    public List<Long> getOrdersByClientId(long id);


}
