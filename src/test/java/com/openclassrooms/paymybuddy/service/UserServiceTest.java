package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUpBeforeTest() {
        userService = new UserService();
    }

    @Test
    public void test_getUsers() {

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        List<User> users = Arrays.asList(user1, user2, user3);

        when(userService.getUsers()).thenReturn(users);

        List<User> userList = (List<User>) userService.getUsers();

        assertEquals(3, userList.size());

    }

    @Test
    public void test_saveUser() throws UserAlreadyExistException {
        user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("test");
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.saveUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void test_saveUser_shouldThrowUserAlreadyExistException() throws UserAlreadyExistException {
        user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("test");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);


        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(user));
    }

    @Test
    public void test_findUserById() throws UserNotFoundException {
        user = new User();
        int id = user.getUserID();

        when(userRepository.existsById(any(int.class))).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));

        userService.findUserById(id);

        verify(userRepository).findById(id);


    }

    @Test
    public void test_findUserById_shouldThrowUserNotFoundException() throws UserNotFoundException {
        user = new User();
        int id = user.getUserID();

        when(userRepository.existsById(any(int.class))).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(id));

    }

    @Test
    public void test_getUsersById() throws UserNotFoundException {
        user = new User();
        int id = user.getUserID();

        when(userRepository.existsById(any(int.class))).thenReturn(true);
        when(userRepository.getById(id)).thenReturn((user));

        userService.getUserById(id);

        verify(userRepository).getById(id);


    }

    @Test
    public void test_getUsersById_shouldThrowUserNotFoundException() throws UserNotFoundException {
        user = new User();
        int id = user.getUserID();

        when(userRepository.existsById(any(int.class))).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));


    }

    @Test
    public void test_removeUserById() throws UserNotFoundException {
        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(true);

        userService.removeUserById(user.getUserID());

        verify(userRepository).deleteById(user.getUserID());
    }

    @Test
    public void test_removeUserById_shouldThrowUserNotFound() throws UserNotFoundException {
        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->userService.removeUserById(user.getUserID()));
    }

    @Test
    public void test_getTotalAccountBalanceByUserId() {

        user = new User();

        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        account1.setBalance(100);
        account2.setBalance(200);

        List<BankAccount> accountList = Arrays.asList(account1,account2);

        user.setAccountList(accountList);

       when(userRepository.getById(anyInt())).thenReturn(user);

        AtomicReference<Double> balance = userService.getTotalAccountBalanceByUserId(user.getUserID());
        double total = (double) balance.get();

        assertEquals( 300.0, total,0.0);


    }

    @Test
    public void test_getTotalAccountBalanceByUserId_whenNotBankAccount_shouldReturnZero() {

        user = new User();


        when(userRepository.getById(anyInt())).thenReturn(user);

        AtomicReference<Double> balance = userService.getTotalAccountBalanceByUserId(user.getUserID());
        double total = (double) balance.get();

        assertEquals( 0.0, total,0.0);


    }

    @Test
    public void test_getTotalAccountBalanceByUserId_shouldConsiderOnlyNotDeletedBankAccount() {

        user = new User();

        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        account1.setBalance(100);
        account2.setBalance(200);

        account2.setDeleted(true);

        List<BankAccount> accountList = Arrays.asList(account1,account2);

        user.setAccountList(accountList);

        when(userRepository.getById(anyInt())).thenReturn(user);

        AtomicReference<Double> balance = userService.getTotalAccountBalanceByUserId(user.getUserID());
        double total = (double) balance.get();

        assertEquals( 100.0, total,0.0);


    }


    @Test
    public void test_findUserByEmail() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setEmail("mail1@mail.fr");
        user2.setEmail("mail2@mail.fr");
        user3.setEmail("mail3@mail.fr");

        List<User> users = Arrays.asList(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(user2,userService.findUserByEmail("mail2@mail.fr"));

    }

    @Test
    public void test_findUserByEmail_shouldReturnNull_whenNoUserFound() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setEmail("mail1@mail.fr");
        user2.setEmail("mail2@mail.fr");
        user3.setEmail("mail3@mail.fr");

        List<User> users = Arrays.asList(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(null,userService.findUserByEmail("mail@mail.fr"));

    }

    @Test
    public void test_updateRoleById() throws UserNotFoundException {
        User user = new User();
        user.setUserRole("ROLE_USER");

        when(userRepository.existsById(user.getUserID())).thenReturn(true);

        userService.updateRoleById(user.getUserID(), "ROLE_ADMIN");

        verify(userRepository).updateRole(user.getUserID(),"ROLE_ADMIN");



    }

    @Test
    public void test_updateRoleById_shouldThrowUserNotFoundException() throws UserNotFoundException {
        User user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->userService.updateRoleById(user.getUserID(), "ROLE_ADMIN"));



    }

    @Test
    public void test_updateDeletedById() throws UserNotFoundException {

        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(true);

        userService.updateDeletedById(user.getUserID());

        verify(userRepository).updateDeleted(user.getUserID());

    }

    @Test
    public void test_updateDeletedById_shouldThrowUserNotFoundException(){

        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->userService.updateDeletedById(user.getUserID()));



    }

    @Test
    public void test_userIsRemovedById() throws UserNotFoundException {
        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(true);
        when(userRepository.getById(user.getUserID())).thenReturn(user);

        assertFalse(userService.userIsRemovedById(user.getUserID()));

    }

    @Test
    public void test_userIsRemovedById_shouldReturnTrue_whenUserDeleted() throws UserNotFoundException {
        user = new User();
        user.setDeleted(true);

        when(userRepository.existsById(user.getUserID())).thenReturn(true);
        when(userRepository.getById(user.getUserID())).thenReturn(user);

        assertTrue(userService.userIsRemovedById(user.getUserID()));

    }

    @Test
    public void test_userIsRemovedById_shouldThrowUserNotFoundException() throws UserNotFoundException {
        user = new User();

        when(userRepository.existsById(user.getUserID())).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->userService.userIsRemovedById(user.getUserID()));

    }


}
