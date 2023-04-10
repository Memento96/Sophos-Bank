package com.sophos.bootcamp.bankapi.services.impl;

import com.sophos.bootcamp.bankapi.entities.Product;
import com.sophos.bootcamp.bankapi.entities.Transaction;
import com.sophos.bootcamp.bankapi.entities.enums.AccountStatus;
import com.sophos.bootcamp.bankapi.entities.enums.AccountType;
import com.sophos.bootcamp.bankapi.entities.enums.TransactionType;
import com.sophos.bootcamp.bankapi.exceptions.BadRequestException;
import com.sophos.bootcamp.bankapi.exceptions.NotFoundException;
import com.sophos.bootcamp.bankapi.repositories.ProductRepository;
import com.sophos.bootcamp.bankapi.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TransactionServiceImplementationTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ProductRepository productRepository;


    TransactionServiceImplementation transactionService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImplementation(transactionRepository, productRepository);
    }

    @Test
    void findTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setId(1l);

        //when
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionRepository.findById(transaction.getId());

        assertEquals(transaction, result.get());
    }

    @Test
    void findTransactionById_DoesNotExist() {
        Transaction transaction = new Transaction();
        transaction.setId(1l);

        //when
        when(transactionRepository.findById(1l)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            transactionService.findTransactionById(transaction.getId());
        });

        assertEquals("Transaction does not exist", exception.getMessage());
    }



    @Test
    void createTransaction() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(10000d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setTransactionType(TransactionType.WITHDRAWAL);
        withdrawalTransaction.setSender(productSender);
        withdrawalTransaction.setTransactionAmount(1000d);

        Transaction depositTransaction = new Transaction();
        depositTransaction.setTransactionType(TransactionType.DEPOSIT);
        depositTransaction.setRecipient(productRecipient);
        depositTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productRecipient));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productSender));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);
        when(transactionRepository.save(withdrawalTransaction)).thenReturn(withdrawalTransaction);
        when(transactionRepository.save(depositTransaction)).thenReturn(depositTransaction);

        Transaction transferResult = transactionService.createTransaction(transferTransaction);
        Transaction withdrawalResult = transactionService.createTransaction(withdrawalTransaction);
        Transaction depositResult = transactionService.createTransaction(depositTransaction);

        assertEquals(transferTransaction, transferResult);
        assertEquals(withdrawalTransaction, withdrawalResult);
        assertEquals(depositTransaction, depositResult);

    }

    @Test
    void processTransferTransaction_SenderDoesNotExist() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(10000d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.empty());
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.of(productRecipient));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        NotFoundException senderException = assertThrows(NotFoundException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("Sender does not exist", senderException.getMessage());

    }

    @Test
    void processTransferTransaction_RecipientDoesNotExist() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(10000d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.of(productSender));
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.empty());
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        NotFoundException recipientException = assertThrows(NotFoundException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("Recipient does not exist", recipientException.getMessage());

    }

    @Test
    void processTransferTransaction_SavingNoFunds() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(0d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.of(productSender));
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.of(productRecipient));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        BadRequestException notFundsException = assertThrows(BadRequestException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("Insufficient funds", notFundsException.getMessage());

    }

    @Test
    void processTransferTransaction_CheckingNoFunds() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.CHECKING);
        productSender.setGmfExempt(true);
        productSender.setBalance(-3000000d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.SAVINGS);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.of(productSender));
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.of(productRecipient));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        BadRequestException notFundsException = assertThrows(BadRequestException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("Insufficient funds", notFundsException.getMessage());

    }

    @Test
    void processTransferTransaction_SenderInactive() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(40000d);
        productSender.setAccountStatus(AccountStatus.INACTIVE);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(15000d);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.of(productSender));
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.of(productRecipient));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        BadRequestException notFundsException = assertThrows(BadRequestException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("This account can not complete debit transactions", notFundsException.getMessage());

    }

    @Test
    void processTransferTransaction_RecipientCancelled() {
        Product productSender = new Product();
        productSender.setId(1l);
        productSender.setAccountType(AccountType.SAVINGS);
        productSender.setGmfExempt(true);
        productSender.setBalance(40000d);

        Product productRecipient = new Product();
        productRecipient.setId(2l);
        productRecipient.setAccountType(AccountType.CHECKING);
        productRecipient.setGmfExempt(true);
        productRecipient.setBalance(0d);
        productRecipient.setAccountStatus(AccountStatus.CANCELLED);

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setRecipient(productRecipient);
        transferTransaction.setSender(productSender);
        transferTransaction.setTransactionAmount(1000d);

        //when
        when(productRepository.findById(productSender.getId())).thenReturn(Optional.of(productSender));
        when(productRepository.findById(productRecipient.getId())).thenReturn(Optional.of(productRecipient));
        when(productRepository.save(productRecipient)).thenReturn(productRecipient);
        when(productRepository.save(productSender)).thenReturn(productSender);
        when(transactionRepository.save(transferTransaction)).thenReturn(transferTransaction);

        BadRequestException notFundsException = assertThrows(BadRequestException.class, () -> {
            transactionService.createTransaction(transferTransaction);
        });

        assertEquals("This account can not complete credit transactions", notFundsException.getMessage());

    }

    @Test
    void listOfTransactions() {
    }
}