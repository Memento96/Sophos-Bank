package com.sophos.bootcamp.bankapi.entities.enums;

public enum EmployeeRole {
    CASHIER("CASHIER");

    private String status;

    EmployeeRole(String status){ this.status = status;}

    public String getStatus(){return status;}
}