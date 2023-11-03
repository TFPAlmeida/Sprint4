package com.sprint4_activity.crm.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
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

    public Client saveClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setLocal(request.getLocal());
        return repository.save(client);
    }

    public List<Client> saveClients(List<ClientRequest> requests) {
        List<Client> clients = new ArrayList<>();
        for (ClientRequest clientRequest : requests) {
            Client client = new Client();
            client.setName(clientRequest.getName());
            client.setLocal(clientRequest.getLocal());
            clients.add(client);
        }

        return repository.saveAll(clients);
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client getClientById(long id) throws ClientNotFoundException {
        if (repository.existsById(id)) {
            return repository.findClientById(id);
        } else {
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
    }

    public Client updateClient(Client client) throws ClientNotFoundException {
        if (!repository.existsById(client.getId())) {
            throw new ClientNotFoundException("Client not found with id: " + client.getId());
        }
        return repository.save(client);
    }

    public ResponseEntity<String> deleteClient(long id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);

        }
        repository.deleteById(id);
        return new ResponseEntity<>("Client deleted", HttpStatus.OK);
    }

    public List<Order> getOrdersForClient(Long id) throws ClientNotFoundException {
        Client client = getClientById(id);
        if(client == null){
            throw new ClientNotFoundException("Client not found with id: " + id);
        }
        return client.getOrders();
    }

    public ResponseEntity<String> delivaryDataStatus(long clientId, long orderId) throws OrderNotFoundException, ClientNotFoundException {
        Client client = getClientById(clientId);
        Order order = client.getOrders().get((int) orderId-1);
        long days = ChronoUnit.DAYS.between(order.getCreationDate(), order.getDeliverDate());
        if(order.getCreationDate().plusDays(1) == order.getDeliverDate()){
            return new ResponseEntity<>("Order vai ser entrega dentro de previsto: " + order.getCreationDate().plusDays(1), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Devido ao tempo climático vai haver um atraso de  " + days + " dias. Data prvista: " + order.getDeliverDate(), HttpStatus.OK);
        }
    }

    public List<Client> getClientByIdRange(long min, long max){
        return repository.getClientByIdRange(min, max);
    }

    public List<Client> findClientByIdRange(long min, long max) {
        return repository.findClientByIdRange(min, max);
    }

    public List<Long> getOrdersByclientId(long id){
        return repository.getOrdersByClientId(id);
    }

}
