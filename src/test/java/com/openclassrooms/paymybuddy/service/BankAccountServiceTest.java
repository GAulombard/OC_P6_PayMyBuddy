package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.BankAccountAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.BankAccountNotFoundException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.ContactService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private BankAccountService bankAccountService;

    private BankAccount bankAccount;

    @BeforeEach
    void setUpBeforeTest() {
        bankAccountService = new BankAccountService();
    }

    @Test
    public void test_getAccounts(){
        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        BankAccount bankAccount3 = new BankAccount();

        List<BankAccount> accounts = Arrays.asList(bankAccount1,bankAccount2,bankAccount3);

        when(bankAccountService.getAccounts()).thenReturn(accounts);

        List<BankAccount> bankAccountList = (List<BankAccount>) bankAccountService.getAccounts();

        assertEquals(3,bankAccountList.size());

    }

    @Test
    public void test_saveBankAccount() throws BankAccountAlreadyExistException {

        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");
        bankAccount.setBic("AAAAAAAAAAA");
        when(bankAccountRepository.existsById(anyString())).thenReturn(false);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        bankAccountService.saveBankAccount(bankAccount);

        verify(bankAccountRepository).save(bankAccount);

    }

    @Test
    public void test_saveBankAccount_shouldThrowBankAccountAlreadyExistException() throws BankAccountAlreadyExistException {

        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");
        bankAccount.setBic("AAAAAAAAAAA");
        when(bankAccountRepository.existsById(anyString())).thenReturn(true);

        assertThrows(BankAccountAlreadyExistException.class,()->bankAccountService.saveBankAccount(bankAccount));

    }

    @Test
    public void test_removeBankAccountsById() throws BankAccountNotFoundException {
        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");

        when(bankAccountRepository.existsById(anyString())).thenReturn(true);

        bankAccountService.removeBankAccountById(bankAccount.getIban());

        verify(bankAccountRepository).deleteById(bankAccount.getIban());

    }

    @Test
    public void test_removeBankAccountsById_shouldThrowBankAccountNotFoundException() throws BankAccountNotFoundException {
        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");

        when(bankAccountRepository.existsById(anyString())).thenReturn(false);

        assertThrows(BankAccountNotFoundException.class,()->bankAccountService.removeBankAccountById(bankAccount.getIban()));

    }

    @Test
    public void test_findAllBankAccountByOwnerId() throws BankAccountNotFoundException {
        User user = new User();
        user.setUserID(1);

        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        BankAccount bankAccount3 = new BankAccount();
        bankAccount1.setIban("FR0000000000000000000000001");
        bankAccount1.setAccountOwner(user);
        bankAccount2.setIban("FR0000000000000000000000002");
        bankAccount2.setAccountOwner(user);
        bankAccount3.setIban("FR0000000000000000000000003");
        bankAccount3.setAccountOwner(user);

        List<BankAccount> accountList = Arrays.asList(bankAccount1,bankAccount2,bankAccount3);
        user.setAccountList(accountList);

        when(bankAccountRepository.findAllBankAccountByOwnerId(1)).thenReturn(accountList);

        List<BankAccount> allAccounts = bankAccountService.findAllBankAccountByOwnerId(1);

        assertEquals(3,allAccounts.size());


    }

    @Test
    public void test_findAllBankAccountByOwnerId_shouldThrowBankAccountNotfoundException() throws BankAccountNotFoundException {
        User user = new User();
        user.setUserID(1);

        when(bankAccountRepository.findAllBankAccountByOwnerId(1)).thenReturn(null);

       assertThrows(BankAccountNotFoundException.class,()->bankAccountService.findAllBankAccountByOwnerId(1));

    }

    @Test
    public void test_getAllContactBankAccountById() throws UserNotFoundException {
        User user = new User();
        user.setUserID(1);
        User contact = new User();
        contact.setUserID(2);

        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        bankAccount1.setIban("FR0000000000000000000000001");
        bankAccount1.setAccountOwner(contact);
        bankAccount2.setIban("FR0000000000000000000000002");
        bankAccount2.setAccountOwner(contact);

        List<User> contactList = Arrays.asList(contact);
        List<BankAccount> accountList = Arrays.asList(bankAccount1,bankAccount2);

        user.setContactList(contactList);

        when(contactService.getAllMyContactById(1)).thenReturn(contactList);
        when(bankAccountRepository.findAll()).thenReturn(accountList);


        assertEquals(2,bankAccountService.getAllContactBankAccountById(1).size());

    }

    @Test
    public void test_getAllContactBankAccountById_shouldThrowUserNotfoundException() throws UserNotFoundException {
        User user = new User();
        user.setUserID(1);
        User contact = new User();
        contact.setUserID(2);

        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        bankAccount1.setIban("FR0000000000000000000000001");
        bankAccount1.setAccountOwner(contact);
        bankAccount2.setIban("FR0000000000000000000000002");
        bankAccount2.setAccountOwner(contact);

        List<User> contactList = Arrays.asList(contact);
        List<BankAccount> accountList = Arrays.asList(bankAccount1,bankAccount2);

        user.setContactList(contactList);

        when(contactService.getAllMyContactById(3)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class,()->bankAccountService.getAllContactBankAccountById(3));

    }

/*    @Test
    public void test_updateBalanceByIban(){
        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");

        when(bankAccountRepository.existsById(bankAccount.getIban())).thenReturn(true);
        when(bankAccountRepository.updateBalanceByIban();)

    }*/

    @Test
    public void test_updateBalanceByIban_shouldThrowBankAccountNotfoundException(){
        bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000000");

        when(bankAccountRepository.existsById(bankAccount.getIban())).thenReturn(false);


        assertThrows(BankAccountNotFoundException.class,()->bankAccountService.updateBalanceByIban(bankAccount.getIban(),100));

    }

/*    @Test
    public void updateDeletedById(){

    }*/
}
