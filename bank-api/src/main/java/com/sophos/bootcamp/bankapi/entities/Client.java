package com.sophos.bootcamp.bankapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "id_number")
    private String idNumber;

    @NotNull
    @Column(name = "names")
    private String names;

    @NotNull
    @Column(name = "last_names")
    private String lastNames;

    @Column(name = "email_address")
    private String emailAddress;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "client_creator")
    private String clientCreator;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "modification_date")
    private Date modificationDate;


    @Column(name = "modification_user")
    private String modificationUser;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


    public Client() {
        this.clientCreator = "Admin";
        this.modificationUser = "Admin";
        this.isDeleted = false;
    }



}
