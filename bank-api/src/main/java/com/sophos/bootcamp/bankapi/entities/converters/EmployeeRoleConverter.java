package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.EmployeeRole;

import javax.persistence.AttributeConverter;

public class EmployeeRoleConverter implements AttributeConverter<EmployeeRole, String> {

    @Override
    public String convertToDatabaseColumn(EmployeeRole employeeRole) {
        return employeeRole.getStatus();
    }

    @Override
    public EmployeeRole convertToEntityAttribute(String s) {
        return EmployeeRole.valueOf(s);
    }
}
