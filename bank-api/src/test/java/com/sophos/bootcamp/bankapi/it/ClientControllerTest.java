package com.sophos.bootcamp.bankapi.it;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ClientControllerTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    public void createClient(){
        Client client1 = new Client();
        client1.setNames("Marcus");
        client1.setLastNames("Junius Brutus");
        client1.setEmailAddress("stabintheback@caesar.com");
        client1.setDateOfBirth(LocalDate.of(1990,06,06));

        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(
                "/client",
                client1,
                Client.class
        );

        Client responseEntityBody = clientResponseEntity.getBody();
        System.out.println(client1.getEmailAddress() + responseEntityBody.getEmailAddress());
        System.out.println(client1.getLastNames() + responseEntityBody.getLastNames());
        System.out.println(client1.getNames() + responseEntityBody.getNames());
        System.out.println(responseEntityBody.getDateOfBirth());
        System.out.println(clientResponseEntity.getStatusCode());


        assertAll("Asserts for client creation",
                () -> assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode()),
                () -> assertEquals(client1.getNames(), responseEntityBody.getNames()),
                () -> assertEquals(client1.getLastNames(), responseEntityBody.getLastNames()),
                () -> assertEquals(client1.getEmailAddress(), responseEntityBody.getEmailAddress()),
                () -> assertEquals(client1.getDateOfBirth(), responseEntityBody.getDateOfBirth())
        );


    }


    @Test
    @Order(2)
    public void createClient_IdWasProvidedError(){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marcus");
        client.setLastNames("Junius Brutus");
        client.setEmailAddress("stabintheback@caesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));

        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity("/client", client, Client.class);

        assertEquals(HttpStatus.BAD_REQUEST, clientResponseEntity.getStatusCode());
    }


    @Test
    @Order(3)
    public void createClient_DoBNotProvidedError(){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marcus");
        client.setLastNames("Junius Brutus");
        client.setEmailAddress("stabintheback@caesar.com");

        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity("/client", client, Client.class);

        assertEquals(HttpStatus.BAD_REQUEST, clientResponseEntity.getStatusCode());

        System.out.println(HttpStatus.BAD_REQUEST);
    }


    @Test
    @Order(4)
    public void getAllClientsTest(){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marcus");
        client.setLastNames("Junius Brutus");
        client.setEmailAddress("stabintheback@caesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));
        clientRepository.save(client);

        ResponseEntity<List<Client>> clientResponseEntity = restTemplate.exchange(
                "/client",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Client>>() {});

        List<Client> clientList = clientResponseEntity.getBody();

        assertAll("All products list assertions",
                () -> assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode()),
                () -> assertNotNull(clientList),
                () -> assertEquals(1, clientList.size()),
                () -> assertEquals(client, clientList.get(0))
        );
    }

    @Test
    @Order(5)
    public void getClientByIdTest(){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marcus");
        client.setLastNames("Junius Brutus");
        client.setEmailAddress("stabintheback@caesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));
        clientRepository.save(client);

        ResponseEntity<Client> clientResponseEntity = restTemplate.getForEntity(
                "/client/" + client.getId(),
                Client.class);

        assertAll("Assert GET request",
                () -> assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode()),
                () -> assertEquals(client, clientResponseEntity.getBody())
        );
    }

//    @Test
//    public void getProductByIdTest(){
//        Client client = new Client();
//        client.setId(1l);
//        client.setNames("Marcus");
//        client.setLastNames("Junius Brutus");
//        client.setEmailAddress("stabintheback@caesar.com");
//        client.setDateOfBirth(LocalDate.of(1990,06,06));
//        clientRepository.save(client);
//        Product product1 = new Product();
//        product1.setAccountCreator(client);
//        product1.setAccountType(AccountType.SAVINGS);
//        product1.setAccountStatus(AccountStatus.INACTIVE);
//        product1.setGmfExempt(true);
//        productRepository.save(product1);
//        Product product2 = new Product();
//        product2.setAccountCreator(client);
//        product2.setAccountType(AccountType.SAVINGS);
//        product2.setAccountStatus(AccountStatus.INACTIVE);
//        product2.setGmfExempt(true);
//        productRepository.save(product2);
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Client> required = new HttpEntity<>(client, headers);
//
//        String url = "/client/" + client.getId() + "/product";
//        ResponseEntity<List<Product>> productResponseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                required,
//                new ParameterizedTypeReference<List<Product>>(){}
//        );
//
//
//        assertAll("Asserting the results of the test",
//                () -> assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode())
//        );
//
//
//    }

    @Test
    @Order(6)
    public void modifyClientTest (){
        Client client = new Client();
        client.setId(1l);
        client.setNames("Marcus");
        client.setLastNames("Junius Brutus");
        client.setEmailAddress("stabintheback@caesar.com");
        client.setDateOfBirth(LocalDate.of(1990,06,06));
        clientRepository.save(client);
        String newEmail = "newemail@gmail.com";
        client.setEmailAddress(newEmail);

        String url = "/client";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Client> requestEntity = new HttpEntity<>(client, headers);
        ResponseEntity<Client> clientResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Client.class
        );

        Client modifiedClient = clientResponseEntity.getBody();
        assertAll("Assert for the controller",
                () -> assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode()),
                () -> assertEquals(newEmail, modifiedClient.getEmailAddress()),
                () -> assertEquals(client.getId(), modifiedClient.getId())
        );
    }

}
