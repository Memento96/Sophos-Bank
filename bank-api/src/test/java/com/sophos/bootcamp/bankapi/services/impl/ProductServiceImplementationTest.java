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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        product.setId(1l);
        product.setAccountStatus(AccountStatus.ACTIVE);
        product.setGmfExempt(true);

        //when
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.modifyProduct(product);

        assertEquals(product, result);
    }

    @Test
    void modifyProduct_ChangesGmfStatusAndOtherAccountsAreGmfExempt() {
        Client client = new Client();
        client.setId(1l);

        Product product1 = new Product();
        product1.setId(1l);
        product1.setAccountStatus(AccountStatus.ACTIVE);
        product1.setAccountCreator(client);
        product1.setGmfExempt(false);

        Product product2 = new Product();
        product2.setId(2l);
        product2.setAccountStatus(AccountStatus.ACTIVE);
        product2.setGmfExempt(true);
        product2.setAccountCreator(client);

        Product product3 = new Product();
        product3.setId(3l);
        product3.setAccountStatus(AccountStatus.ACTIVE);
        product3.setGmfExempt(false);
        product3.setAccountCreator(client);

        List<Product> clientProducts = new ArrayList<>();
        clientProducts.add(product1);
        clientProducts.add(product2);
        clientProducts.add(product3);


        //when
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepository.findAllByAccountCreatorId(1l)).thenReturn(clientProducts);
        when(productRepository.save(product1)).thenReturn(product1);

        Product productGmfExempt = new Product();
        productGmfExempt.setId(1l);
        productGmfExempt.setGmfExempt(true);

        Product result = productService.modifyProduct(productGmfExempt);

        assertEquals(false, product2.getGmfExempt());
        verify(productRepository, times(2)).save(any());
    }


    @Test
    void modifyProduct_ProductDoesNotExist() {
        Product product = new Product();
        product.setId(1l);

        //when
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.modifyProduct(product);
        });

        assertEquals("Product is not in system", exception.getMessage());
    }

    @Test
    void modifyProduct_ProductIsCancelled() {
        Product product = new Product();
        product.setId(1l);
        product.setAccountStatus(AccountStatus.CANCELLED);

        //when
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productService.modifyProduct(product);
        });

        assertEquals("This account can not be modified as it has already been cancelled", exception.getMessage());
    }

    @Test
    void modifyProduct_ProductHasBalanceCantBeCancelled() {

        Product product = new Product();
        product.setId(1l);
        product.setAccountStatus(AccountStatus.ACTIVE);
        product.setBalance(1000d);

        //when
        when(productRepository.findById(1l)).thenReturn(Optional.of(product));

        Product productCancelled = new Product();
        productCancelled.setId(1l);
        productCancelled.setAccountStatus(AccountStatus.CANCELLED);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productService.modifyProduct(productCancelled);
        });

        assertEquals("Account can not be closed as it has a balance", exception.getMessage());
    }

    @Test
    void modifyProduct_GmfFalseIfProductIsCancelled() {

        Product product = new Product();
        product.setId(1l);
        product.setAccountStatus(AccountStatus.ACTIVE);
        product.setBalance(0d);
        product.setGmfExempt(true);

        //when
        when(productRepository.findById(1l)).thenReturn(Optional.of(product));

        Product productCancelled = new Product();
        productCancelled.setId(1l);
        productCancelled.setAccountStatus(AccountStatus.CANCELLED);
        productCancelled.setGmfExempt(true);

        Product result = productService.modifyProduct(productCancelled);

        assertEquals(false, productCancelled.getGmfExempt());

    }


    @Test
    void testGetAccountNumber() {
        Client client = new Client();
        client.setId(1l);

        Product savingsAccount = new Product();
        savingsAccount.setAccountCreator(client);
        savingsAccount.setId(1l);
        savingsAccount.setAccountType(AccountType.SAVINGS);
        savingsAccount.setGmfExempt(true);

        Product checkingAccount = new Product();
        checkingAccount.setAccountCreator(client);
        checkingAccount.setId(2l);
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setGmfExempt(true);

        //when
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(productRepository.save(any())).thenReturn(savingsAccount);
        when(productRepository.save(any())).thenReturn(checkingAccount);

        Product resultSavings = productService.createProduct(savingsAccount);
        assertEquals("4600000002", savingsAccount.getAccountNumber());

        Product resultChecking = productService.createProduct(checkingAccount);
        assertEquals("2300000002", checkingAccount.getAccountNumber());

        }


    }