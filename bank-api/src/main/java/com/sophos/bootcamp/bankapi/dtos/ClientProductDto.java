package com.sophos.bootcamp.bankapi.dtos;

import com.sophos.bootcamp.bankapi.entities.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientProductDto {
    public static List<ProductDto> mapToDto(List<Product> product) {
        return product.stream()
                .map(tx -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(tx.getId());
                    productDto.setAccountCreator(tx.getAccountCreator().getId());
                    productDto.setAccountType(tx.getAccountType().getStatus());
                    productDto.setAccountNumber(tx.getAccountNumber());
                    productDto.setAccountStatus(tx.getAccountStatus().getStatus());
                    productDto.setBalance(tx.getBalance());
                    productDto.setAvailableBalance(tx.getAvailableBalance());
                    productDto.setGmfExempt(tx.getGmfExempt());
                    productDto.setCreationDate(tx.getCreationDate());
                    productDto.setModificationDate(tx.getModificationDate());
                    productDto.setModificationUser(tx.getModificationUser());
                    return productDto;
                })
                .collect(Collectors.toList());
    }

}


