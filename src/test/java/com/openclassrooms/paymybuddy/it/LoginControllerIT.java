package com.openclassrooms.paymybuddy.it;


import com.openclassrooms.paymybuddy.controller.LoginController;
import com.openclassrooms.paymybuddy.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManagerFactory;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@RunWith(SpringRunner.class)
//@WebMvcTest(LoginController.class)
public class LoginControllerIT {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

/*    @MockBean
    private UserService userService;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private FeeService feeService;

    @MockBean
    private ContactService contactService;

    @MockBean
    private MyUserDetailsService myUserDetailsService;
    */

    @BeforeEach
    public void setupBeforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void test_getLoginPage() throws Exception {
        mockMvc.perform(get("/login").with(anonymous()))
                .andExpect(status().isOk());
    }
}
