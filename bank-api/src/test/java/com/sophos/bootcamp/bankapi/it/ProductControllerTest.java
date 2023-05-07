package com.sophos.bootcamp.bankapi.it;

import com.sophos.bootcamp.bankapi.dtos.ProductDto;
import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import com.sophos.bootcamp.bankapi.repositories.ClientRepository;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    //constant variables
    private String url = "/product";


    @Test
    public void createProductTest(){
        Client client1 = new Client();
        client1.setNames("Marcus");
        client1.setLastNames("Junius Brutus");
        client1.setEmailAddress("stabintheback@caesar.com");
        client1.setIdNumber("1234");
        client1.setDateOfBirth(LocalDate.of(1990,06,06));
        Client savedClient = clientRepository.save(client1);
        Product product = new Product();
        product.setGmfExempt(true);
        product.setAccountCreator(savedClient);
        product.setAccountType(AccountType.SAVINGS);

        ResponseEntity<ProductDto> responseEntity = restTemplate.postForEntity(
                "/product",
                product,
                ProductDto.class
        );

        ProductDto responseEntityBody = responseEntity.getBody();

        assertAll("Asserts for the creation Method",
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );



    }


}
