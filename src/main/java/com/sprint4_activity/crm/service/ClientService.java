package com.sprint4_activity.crm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sprint4_activity.crm.dtos.ClientDTOs;
import com.sprint4_activity.crm.dtos.OrderDTOs;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.repository.ClientRepository;
import com.sprint4_activity.crm.request.ClientRequest;
import com.sprint4_activity.crm.request.OrderRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {


    private ClientRepository repository;

    public ClientDTOs saveClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        return new ClientDTOs(repository.save(client));
    }

    public List<ClientDTOs> saveClients(List<ClientRequest> requests) {
        List<Client> clients = new ArrayList<>();
        List<ClientDTOs> clientDTOsList = new ArrayList<>();
        for (ClientRequest clientRequest : requests) {
            Client client = new Client();
            client.setName(clientRequest.getName());
            clients.add(client);
            ClientDTOs clientDTOs = new ClientDTOs(client);
            clientDTOsList.add(clientDTOs);
        }
        repository.saveAll(clients);
        return clientDTOsList;
    }

    public List<ClientDTOs> getAllClients() {
        List<ClientDTOs> clientDTOsList = new ArrayList<>();
        for (Client client: repository.findAll()) {
            ClientDTOs clientDTOs = new ClientDTOs(client);
            clientDTOsList.add(clientDTOs);
        }
        return clientDTOsList;
    }

    public ClientDTOs getClientById(long id) throws ClientNotFoundException {
        if (repository.existsById(id)) {
            return new ClientDTOs(repository.findClientById(id));
        } else {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
    }

    public ClientDTOs updateClient(Client client) throws ClientNotFoundException {
        if (!repository.existsById(client.getId())) {
            throw new ClientNotFoundException("Client not found with id: " + client.getId());
        }
        return new ClientDTOs(repository.save(client));
    }

    public ResponseEntity<String> deleteClient(long id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);

        }
        repository.deleteById(id);
        return new ResponseEntity<>("Client deleted", HttpStatus.OK);
    }

    public List<OrderDTOs> getOrdersForClient(Long id) throws ClientNotFoundException {
        ClientDTOs clientDTOs = getClientById(id);
        if(clientDTOs == null){
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
        List<OrderDTOs>orderDTOsList = new ArrayList<>();
        for (Order order:clientDTOs.getOrders()) {
            OrderDTOs orderDTOs = new OrderDTOs(order);
            orderDTOsList.add(orderDTOs);
        }
        return orderDTOsList;
    }


}
