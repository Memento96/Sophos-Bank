package com.sophos.bootcamp.bankapi.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sophos.bootcamp.bankapi.entities.Client;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateClientDto {

    private Long id;

    private String names;

    private String lastNames;

    private String emailAddress;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date modificationDate;

    public Client mapToDto(){
        Client client = new Client();
        client.setId(id);
        client.setNames(names);
        client.setLastNames(lastNames);
        client.setEmailAddress(emailAddress);
        client.setModificationDate(new Date());
        return client;
    }
}
