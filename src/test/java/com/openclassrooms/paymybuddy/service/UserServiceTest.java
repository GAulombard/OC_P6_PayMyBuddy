package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUpBeforeTest(){
        userService = new UserService();
    }

    @Test
    public void test_getUsers() {

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        List<User> users = Arrays.asList(user1,user2,user3);

        when(userService.getUsers()).thenReturn(users);

        assertEquals(3,users.size());

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


        assertThrows(UserAlreadyExistException.class,()->userService.saveUser(user));
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

}
