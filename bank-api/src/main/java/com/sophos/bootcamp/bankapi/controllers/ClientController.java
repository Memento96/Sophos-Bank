package com.sophos.bootcamp.bankapi.controllers;

import com.sophos.bootcamp.bankapi.dtos.ClientProductDto;
import com.sophos.bootcamp.bankapi.dtos.ProductDto;
import com.sophos.bootcamp.bankapi.dtos.UpdateClientDto;
import com.sophos.bootcamp.bankapi.entities.Client;
import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.services.ClientService;
import com.sophos.bootcamp.bankapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:4200/")
public class ClientController {

    private final ClientService clientService;
    private final ProductService productService;

    public ClientController(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if (client.getId() != null){
            throw new IllegalArgumentException("Do not provide ID number, this will be automatically created by the system");
        }
        Client clientCreated = clientService.createClient(client);
        return new ResponseEntity<>(clientCreated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable Long id){
        Optional<Client> clientById = clientService.findClientById(id);
        return new ResponseEntity<>(clientById, HttpStatus.OK);
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<List<ProductDto>> getProductByClientId(@PathVariable Long id) {
        List<Product> allProductsByClientId = productService.getAllProductsByClientId(id);
        List<ProductDto> filteredProductJson = ClientProductDto.mapToDto(allProductsByClientId);
        return new ResponseEntity<>(filteredProductJson, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Client> modifyClient(@RequestBody UpdateClientDto client) {
        Client modifiedClient = clientService.modifyClient(client.mapToDto());
        return new ResponseEntity<>(modifiedClient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteClientById(@PathVariable("id") Long id){
        if (clientService.deleteClientById(id)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
