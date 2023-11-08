package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.request.ClientRequest;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@Data
@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Client successfully from DB")
    void getClientByIdRangeCase1() {
        ClientRequest request = new ClientRequest("Tiago","Porto");
        createClient(request);

        List<Client> result = clientRepository.findClientByIdRange(0,1);

        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Should not get Client from DB when Client not exists")
    void getClientByIdRangeCase2() {

        List<Client> result = clientRepository.findClientByIdRange(0,1);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should get Client successfully from DB")
    void findClientByIdRangeCase1() {
        ClientRequest request = new ClientRequest("Tiago","Porto");
        createClient(request);

        List<Client> result = clientRepository.findClientByIdRange(0,1);

        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Should not get Client from DB when Client not exists")
    void findClientByIdRangeCase2() {

        List<Client> result = clientRepository.findClientByIdRange(0,1);

        assertThat(result.isEmpty()).isTrue();
    }

    public Client createClient(ClientRequest request){
        Client client = new Client();
        client.setName(request.getName());
        client.setLocal(request.getLocal());
        entityManager.persist(client);
        return client;
    }
}