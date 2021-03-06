package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
//@AutoConfigureMockMvc
//@DataJpaTest
//@RunWith(SpringRunner.class)
public class AnonymousControllerTest {

    //@Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setupBeforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void test_getSignupForm() throws Exception {
        mockMvc.perform(get("/signup").with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));

    }

    @Test
    void test_getSignupForm_whenAdminRegistered() throws Exception {
        mockMvc.perform(get("/signup").with(httpBasic("g.aulombard@jetmail.fr","1234123456789")))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void test_getSignupForm_whenUserRegistered() throws Exception {
        mockMvc.perform(get("/signup").with(httpBasic("j.bombeurre@jetmail.fr","1234123456789")))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void test_getIndex() throws Exception {
        mockMvc.perform(get("/index").with(anonymous()))
                .andExpect(status().isOk());

    }

    @Test
    @Rollback
    @Transactional
    void test_addNewUser() throws Exception {
        User user = new User();
        //user.setUserID(10);
        user.setFirstName("Jeannot");
        user.setLastName("Michel");
        user.setPassword("12345");
        user.setUserRole("ROLE_USER");
        user.setEmail("jean.michel13@mail.fr");
        user.setAddress("rue du test");
        user.setZip(45000);
        user.setCity("test");
        user.setPhone(0000000000);

        mockMvc.perform(post("/signup/save").with(anonymous()).flashAttr("user",user))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @Rollback
    @Transactional
    void test_addNewUser_withFieldError() throws Exception {
        User user1 = new User();
        user1.setUserID(12);
        user1.setFirstName("Jean");
        user1.setLastName("Michel2");
        user1.setPassword("1");
        user1.setUserRole("ROLE_USER");
        user1.setEmail("jean.michel2@mail.fr");
        user1.setAddress("rue du test");
        user1.setZip(45000);
        user1.setCity("test");
        user1.setPhone(0000000000);

        mockMvc.perform(post("/signup/save").with(anonymous()).flashAttr("user",user1))
                .andExpect(status().isOk());

    }
}
