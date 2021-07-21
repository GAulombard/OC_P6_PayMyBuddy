package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUpBeforeTest(){

    }

    @Test
    public void test_getUsers() {

/*        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        Iterable<User> users = Arrays.asList(user1,user2,user3);

        when(userRepository.findAll()).thenReturn((List<User>) users);



        assertEquals(3,);*/

        /*Iterable<User> users = userService.getUsers();
        List<User> userList = new ArrayList<>();

        users.forEach(userList::add);

        assertEquals(7,userList.size());*/

    }

}
