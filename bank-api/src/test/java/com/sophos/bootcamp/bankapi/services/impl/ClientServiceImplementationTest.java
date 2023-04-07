package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ClientServiceImplementationTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductRepository productRepository;

    private ClientServiceImplementation clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImplementation(clientRepository, productRepository);
    }

    @Test
    void findClientById() {
        Client clientExpected = new Client();
        clientExpected.setId(1l);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientExpected));

        Optional<Client> clientById = clientService.findClientById(1l);
        assertEquals(clientExpected, clientById.get());
    }

    @Test
    void findClientByIdShouldThrowException() {
        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            clientService.findClientById(1l);
        });
    }

    @Test
    void createClient() {
        Client client = new Client();
        client.setEmailAddress("scifi@gmail.com");
        client.setDateOfBirth(LocalDate.of(1990, 1, 1));
        client.setNames("Philip");
        client.setLastNames("K. Dick");

        //when
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.createClient(client);

        assertEquals(client, result);
    }

    @Test
    void createClientWithAGenericMock() {
        Client client = new Client();
        client.setEmailAddress("scifi@gmail.com");
        client.setDateOfBirth(LocalDate.of(1990, 1, 1));
        client.setNames("Philip");
        client.setLastNames("K. Dick");

        //when
        when(clientRepository.save(any())).thenReturn(client);

        Client result = clientService.createClient(client);

        assertEquals(client, result);
    }

    @Test
    void createClient_ThrowsClientExistsException() {
        //give
        Client existingClient = new Client();
        existingClient.setId(1l);

        //when
        when(clientRepository.findByIdNumber(existingClient.getIdNumber()))
                .thenReturn(Optional.of(existingClient));

        Client badClient = new Client();
        badClient.setId(existingClient.getId());

        //then
        assertThrows(BadRequestException.class, () -> {
            clientService.createClient(existingClient);
        });
    }

    @Test
    void createClientWithNameTooShort() {
        Client badClient = new Client();
        badClient.setEmailAddress("scifi@gmail.com");
        badClient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        badClient.setNames("P");
        badClient.setLastNames("K. Dick");

        assertThrows(BadRequestException.class, () -> {
            clientService.createClient(badClient);
        });
    }

    @Test
    void createClientWithLastNameTooShort() {
        Client badClient = new Client();
        badClient.setEmailAddress("scifi@gmail.com");
        badClient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        badClient.setNames("Philip");
        badClient.setLastNames("K");

        assertThrows(BadRequestException.class, () -> {
            clientService.createClient(badClient);
        });
    }

    @Test
    void createClientWithIncorrectEmailFormat() {
        Client badClient = new Client();
        badClient.setEmailAddress("scifigmail.com");
        badClient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        badClient.setNames("Philip");
        badClient.setLastNames("K Dick");

        assertThrows(BadRequestException.class, () -> {
            clientService.createClient(badClient);
        });
    }

    @Test
    void createClientUnderAgeError() {
        Client badClient = new Client();
        badClient.setEmailAddress("scifi@gmail.com");
        badClient.setDateOfBirth(LocalDate.of(2019, 1, 1));
        badClient.setNames("Philip");
        badClient.setLastNames("K Dick");

        assertThrows(BadRequestException.class, () -> {
            clientService.createClient(badClient);
        });
    }


    @Test
    void getAllClients() {
        List<Client> clientList = new ArrayList<>();

        //when
        when(clientRepository.findAll()).thenReturn(clientList);

        List<Client> getAllClients = clientService.getAllClients();
        assertEquals(clientList, getAllClients);
    }

    @Test
    void getAllClientsThrowsException() {

        //when
        when(clientRepository.findAll()).thenThrow(new BadRequestException());

        assertThrows(BadRequestException.class, () -> {
            clientService.getAllClients();
        });
    }

    @Test
    void modifyClient() {
        Client client = new Client();
        client.setId(1l);
        client.setNames("Edgar Allan");
        client.setLastNames("Poe");
        client.setEmailAddress("theraven@gmail.com");

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.modifyClient(client);

        assertEquals(client, result);
    }

    @Test
    void modifyClient_ClientDoesNotExist() {
        Client client = new Client();
        client.setId(1l);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            clientService.modifyClient(client);
        });
    }

    @Test
    void modifyClientEmailIsWrongFormat() {
        Client client = new Client();
        client.setId(1l);
        client.setNames("Edgar Allan");
        client.setLastNames("Poe");
        client.setEmailAddress("theravengmail.com");

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        assertThrows(BadRequestException.class, () -> {
            clientService.modifyClient(client);
        });
    }



    @Test
    void deleteClientById() {
        Client client = new Client();
        client.setId(1l);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        Boolean result = clientService.deleteClientById(client.getId());

        //Whatever of this three options can be used to test the function
        assertTrue(result);
        assertEquals(true, result);
        assertEquals(Boolean.TRUE, result);
    }

    @Test
    void deleteClientById_ThrowsNotFoundException() {
        Client client = new Client();
        client.setId(1l);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            clientService.deleteClientById(client.getId());
        });
    }

    @Test
    void deleteClientById_ThrowsBadRequestException() {
        Client client = new Client();
        client.setId(1l);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setAccountStatus(AccountStatus.ACTIVE);
        products.add(product);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.findAllByAccountCreatorId(anyLong())).thenReturn(products);

        assertThrows(BadRequestException.class, () -> {
            clientService.deleteClientById(client.getId());
        });
    }
}