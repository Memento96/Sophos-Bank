package com.sophos.bootcamp.bankapi.security;

import com.sophos.bootcamp.bankapi.entities.Employee;
import com.sophos.bootcamp.bankapi.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailServiceImpl implements UserDetailsService {


    private final EmployeeRepository employeeRepository;

    public EmployeeDetailServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository
                .findOneByEmployeeEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The employee email " + email + " was not found"));

        return new EmployeeDetailsImpl(employee);
    }
}
