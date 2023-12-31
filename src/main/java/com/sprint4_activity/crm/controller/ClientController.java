package com.sprint4_activity.crm.controller;

import java.util.List;

import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.request.ClientRequest;
import com.sprint4_activity.crm.request.OrderRequest;
import com.sprint4_activity.crm.service.ClientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService service;

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody @Valid ClientRequest clientRequest) {
        return new ResponseEntity<>(service.saveClient(clientRequest), HttpStatus.CREATED);
    }

    @PostMapping("/addClients")
    public ResponseEntity<List<Client>> addClients(@RequestBody @Valid List<ClientRequest> clientRequest) {
        return new ResponseEntity<>(service.saveClients(clientRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getClients")
    public ResponseEntity<List<Client>> findAllClients() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable long id) throws ClientNotFoundException {
        return ResponseEntity.ok(service.getClientById(id));
    }

    @GetMapping("/getClientOrdersById/{id}")
    public ResponseEntity<List<Order>> getClientOrdersById(@PathVariable long id) throws ClientNotFoundException {
        return ResponseEntity.ok(service.getOrdersForClient(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws ClientNotFoundException {
        return ResponseEntity.ok(service.updateClient(client));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable long id) {
        return service.deleteClient(id);
    }

}
