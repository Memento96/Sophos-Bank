package com.sophos.bootcamp.bankapi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    public Long id;

    @Column(name = "employee_email")
    public String employeeEmail;

    @Column(name = "password")
    public String password;

    @Column(name = "employee_name")
    public String employeeName;

    @Column(name = "employee_last_name")
    public String employeeLastName;

    @Column(name = "employee_role")
    public String role;



}
