package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    private MyUserDetails myUserDetails;

    @BeforeEach
    void setUpBeforeTest() {

    }

    @Test
    public void test_loadUserByUserName(){
        User user = new User();
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserRole("ROLE_USER");
        user.setZip(45000);
        user.setCity("city");
        user.setPassword("1234");
        user.setEmail("test@test.mail");
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());

        assertEquals("Jean Michel",userDetails.getUsername());

    }
}
