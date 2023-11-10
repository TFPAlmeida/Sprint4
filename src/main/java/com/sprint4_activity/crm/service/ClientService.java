package com.sprint4_activity.crm.service;


import java.util.List;

import java.util.stream.Collectors;

import com.sprint4_activity.crm.dtos.ClientDTOs;
import com.sprint4_activity.crm.dtos.OrderDTOs;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.repository.ClientRepository;
import com.sprint4_activity.crm.request.ClientRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private ClientRepository repository;

    /*
     * clients.stream(): Isso transforma a lista clients em um stream, que é uma sequência de elementos que podem ser
     * processados em paralelo ou de forma sequencial.
     * .map(client -> modelMapper.map(client, ClientDTOs.class)): Aqui, usamos o método map para aplicar uma função a cada
     * elemento do stream. Neste caso, a função recebe um client da lista e usa o ModelMapper para mapeá-lo para um objeto
     * ClientDTOs. Então, para cada client na lista, obtemos um novo objeto ClientDTOs.
     * .collect(Collectors.toList()): Finalmente, usamos o método collect para reunir os resultados de volta em uma lista.
     * O Collectors.toList() especifica que queremos coletar os elementos em uma lista.
     * */

    public ClientDTOs saveClient(ClientRequest request) throws ClientNotFoundException {

        ModelMapper modelMapper = new ModelMapper();
        // Mapear as propriedades de ClientRequest para Client
        Client client = modelMapper.map(request, Client.class);

        // Verificar nulidade antes de tentar salvar
        if (client == null) {
            throw new ClientNotFoundException("Cliente fornecido é nulo. Não é possível salvar.");
        }
        try {
            // Salvar o cliente e criar um ClientDTOs a partir do resultado
            return modelMapper.map(repository.save(client), ClientDTOs.class);
        } catch (DataAccessException e) {
            log.error("Erro ao salvar o cliente.", e);
            throw new ClientNotFoundException("Erro ao salvar o cliente. Detalhes: " + e.getMessage());
        }
    }

    public List<ClientDTOs> saveClients(List<ClientRequest> requests) throws ClientNotFoundException {
        // Inicializa o ModelMapper, uma ferramenta para mapeamento de objetos
        ModelMapper modelMapper = new ModelMapper();
        // Mapeia os objetos ClientRequest para Client usando o ModelMapper
        List<Client> clients = requests.stream()
                .map(client -> modelMapper.map(client, Client.class))
                .collect(Collectors.toList());

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("Clientes fornecidos são nulos. Não é possível salva-los.");
        }

        try {
            // Salva todos os objetos Client no repositório
            repository.saveAll(clients);
            // Mapeia os objetos Client para ClientDTOs usando o ModelMapper
            return clients.stream()
                    .map(client -> modelMapper.map(client, ClientDTOs.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("Erro ao salvar os clientes.", e);
            throw new ClientNotFoundException("Erro ao salvar os clientes. Detalhes: " + e.getMessage());
        }
    }

    public List<ClientDTOs> getAllClients() throws ClientNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if (repository.findAll().isEmpty()) {
            throw new ClientNotFoundException("Não foram encontrados clientes na DB.");
        }
        try {
            return repository.findAll().stream()
                    .map(client -> modelMapper.map(client, ClientDTOs.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("Erro a obter clientes.", e);
            throw new ClientNotFoundException("Erro a obter clientes. Detalhes: " + e.getMessage());
        }

    }

    public ClientDTOs getClientById(long id) throws ClientNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if (!repository.existsById(id)){
            throw new ClientNotFoundException("Não existe cliente com o id: " + id + " na DB");
        }

        try {
            return modelMapper.map(repository.findClientById(id), ClientDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro a obter o cliente.", e);
            throw new ClientNotFoundException("Erro a obter o cliente com id: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    public ClientDTOs updateClient(Client client) throws ClientNotFoundException {
        ModelMapper modelMapper = new ModelMapper();


        if (!repository.existsById(client.getId())) {
            throw new ClientNotFoundException("Erro a atualizar o cliente com id: " + client.getId());
        }

        try {
            return modelMapper.map(repository.save(client), ClientDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro a atualizar o cliente.", e);
            throw new ClientNotFoundException("Erro a atualizar o cliente com id: " + client.getId() + ". Detalhes: " + e.getMessage());
        }

    }

    public ResponseEntity<String> deleteClient(long id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);

        }
        repository.deleteById(id);
        return new ResponseEntity<>("Client deleted", HttpStatus.OK);
    }

    public List<OrderDTOs> getOrdersForClient(Long id) throws ClientNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        ClientDTOs clientDTOs = getClientById(id);
        if (clientDTOs == null) {
            throw new ClientNotFoundException("Erro a obter o cliente com id: " + id);
        }

        try {
            return clientDTOs.getOrders().stream()
                    .map(order -> modelMapper.map(order, OrderDTOs.class))
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Erro a obter o cliente.", e);
            throw new ClientNotFoundException("Erro a atualizar o cliente com id: " + clientDTOs.getId() + ". Detalhes: " + e.getMessage());
        }

    }


}
