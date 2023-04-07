package com.sophos.bootcamp.bankapi.it;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ClientControllerTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestRestTemplate restTemplate;



    @Test
    public void createClient(){
        Client client = new Client();
        client.setNames("Marco");
        client.setLastNames("Aurelio");
        client.setEmailAddress("stabinTheback@cesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));

        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity("/client", client, Client.class);

        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        Client clientSaved = clientResponseEntity.getBody();
        Client clientById = clientRepository.findById(clientSaved.getId()).get();
        assertEquals(clientSaved.getId(), clientById.getId());
        assertEquals(clientSaved.getNames(), clientById.getNames());
        assertEquals(clientSaved.getLastNames(), clientById.getLastNames());
        assertEquals("stabintheback@cesar.com", clientById.getEmailAddress());
        assertEquals(clientSaved.getDateOfBirth(), clientById.getDateOfBirth());

    }

    @Test
    public void createClient_IdWasProvidedError(){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marco");
        client.setLastNames("Aurelio");
        client.setEmailAddress("stabintheback@cesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));

        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity("/client", client, Client.class);

        assertEquals(HttpStatus.BAD_REQUEST, clientResponseEntity.getStatusCode());
    }
}
