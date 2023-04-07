package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProductServiceImplementationTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ClientRepository clientRepository;

    ProductServiceImplementation productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImplementation(productRepository, clientRepository);
    }

    @Test
    void getAccountNumber() {

        ProductServiceImplementation productService = new ProductServiceImplementation(null, null);
        String accountNumber = productService.getAccountNumber("11", "1");
        Assertions.assertEquals("1100000001", accountNumber);

        String accountNumber2 = productService.getAccountNumber("11", "35");
        Assertions.assertEquals("1100000035", accountNumber2);
    }

    @Test
    void createProduct() {
        Client client = new Client();
        client.setId(1l);
        Product product = new Product();
        product.setAccountCreator(client);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void createProduct_ClientDoesNotExist() {
        Client client = new Client();
        client.setId(1l);
        Product product = new Product();
        product.setAccountCreator(client);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.createProduct(product);
        });
    }

    @Test
    void createProduct_TurnsGmfFalse() {
        Client client = new Client();
        client.setId(1l);
        Product product = new Product();
        product.setId(1l);
        product.setAccountType(AccountType.SAVINGS);
        Product product2 = new Product();
        product2.setGmfExempt(true);
        product.setAccountCreator(client);
        List<Product> products = new ArrayList<>();
        products.add(product2);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.findAllByAccountCreatorId(client.getId())).thenReturn(products);
        when(productRepository.save(product)).thenReturn(product);

        Boolean result = productService.createProduct(product).getGmfExempt();

        assertFalse(product.getGmfExempt());

    }

    @Test
    void createProduct_ClientIsDeleted() {
        Client client = new Client();
        client.setId(1l);
        client.setIsDeleted(true);
        Product product = new Product();
        product.setAccountCreator(client);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        assertThrows(BadRequestException.class, () -> {
            productService.createProduct(product);
        });

    }



    @Test
    void getAllProducts() {

        List<Product> products = new ArrayList<>();

        //when
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(products, result);

    }

    @Test
    void getAllProductsByClientId() {
        Client client = new Client();
        client.setId(1l);
        List<Product> products = new ArrayList<>();


        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.findAllByAccountCreatorId(anyLong())).thenReturn(products);

        List<Product> result = productService.getAllProductsByClientId(client.getId());

        assertEquals(products, result);
    }

    @Test
    void getAllProductsByClientId_ClientDoesNotExist() {
        Client client = new Client();
        client.setId(1l);

        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.getAllProductsByClientId(client.getId());
        });
    }

    @Test
    void getProductById() {
        Product product = new Product();
        product.setId(1l);
        Optional<Product> products = Optional.of(product);

        //when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(product.getId());

        assertEquals(products, result);
    }

    @Test
    void getProductById_ProductDoesNotExistError() {
        Product product = new Product();
        product.setId(1l);

        //when
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.getProductById(product.getId());
        });
    }

    @Test
    void modifyProduct() {
        Product product = new Product();
                

    }

    @Test
    void testGetAccountNumber() {
    }
}