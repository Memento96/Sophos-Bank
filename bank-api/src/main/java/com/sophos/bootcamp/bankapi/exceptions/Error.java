package com.sophos.bootcamp.bankapi.exceptions;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Error {

    private String message;

    private LocalDate date;

    public Error(String message) {
        this.message = message;
        this.date = LocalDate.now();
    }

}
