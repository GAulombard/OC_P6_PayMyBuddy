package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BankAccountNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import org.hibernate.type.LocalDateTimeType;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUpBeforeTest() {
        transaction = new Transaction();
    }

    @Test
    public void test_getTransactions(){
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();


        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        when(transactionService.getTransactions()).thenReturn(transactions);

        List<Transaction> transactionList = (List<Transaction>) transactionService.getTransactions();

        assertEquals(3, transactionList.size());
    }

    @Test
    public void test_findAllTransactionsByBankAccountIban(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();


        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        when(transactionRepository.findAllTransactionsByBankAccountIban(anyString())).thenReturn(transactions);

        assertEquals(3,transactionService.findAllTransactionsByBankAccountIban(bankAccount.getIban()).size());

    }

    @Test
    public void test_findAllTransactionsByUserId() throws BankAccountNotFoundException {
        User user = new User();
        BankAccount bankAccount = new BankAccount();
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        bankAccount.setIban("FR0000000000000000000000000");
        bankAccount.setAccountOwner(user);
        transaction1.setCreditor(bankAccount);
        transaction1.setReference(1);
        transaction1.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction2.setDebtor(bankAccount);
        transaction2.setReference(2);
        transaction2.setDate(LocalDateTime.parse("2020-12-15T15:14:21.629"));

        List<BankAccount> accountList = Arrays.asList(bankAccount);
        List<Transaction> transactionList = Arrays.asList(transaction1,transaction2);
        user.setAccountList(accountList);

        when(bankAccountService.findAllBankAccountByOwnerId(user.getUserID())).thenReturn(accountList);
        when(transactionService.findAllTransactionsByBankAccountIban(anyString())).thenReturn(transactionList);

        assertEquals(2,transactionService.findAllTransactionsByUserId(user.getUserID()).size());
    }

    @Test
    public void test_saveTransaction(){

        transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.saveTransaction(transaction);

        verify(transactionRepository).save(transaction);

    }

}
