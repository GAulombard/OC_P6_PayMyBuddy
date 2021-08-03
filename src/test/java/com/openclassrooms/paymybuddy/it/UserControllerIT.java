package com.openclassrooms.paymybuddy.it;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setupBeforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void test_getHome_withAdmin() throws Exception {
        mockMvc.perform(get("/user/home").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("total","accounts"));
    }

    @Test
    void test_getHome_withUser() throws Exception {
        mockMvc.perform(get("/user/home").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("total","accounts"));
    }
    @Test
    void test_getHome_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/home").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getContact_withAdmin() throws Exception {
        mockMvc.perform(get("/user/contact").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users","contacts","contactsOf"));
    }

    @Test
    void test_getContact_withUser() throws Exception {
        mockMvc.perform(get("/user/contact").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users","contacts","contactsOf"));
    }
    @Test
    void test_getContact_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/contact").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getProfile_withAdmin() throws Exception {
        mockMvc.perform(get("/user/profile").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void test_getProfile_withUser() throws Exception {
        mockMvc.perform(get("/user/profile").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void test_getProfile_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/profile").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getTransfer_withAdmin() throws Exception {
        mockMvc.perform(get("/user/transfer").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user","feeRate","transaction","contactAccount","myAccounts","transactions"));
    }

    @Test
    void test_getTransfer_withUser() throws Exception {
        mockMvc.perform(get("/user/transfer").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user","feeRate","transaction","contactAccount","myAccounts","transactions"));
    }
    @Test
    void test_getTransfer_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/transfer").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_deleteUserAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/user/deletemypmb").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void test_deleteUserAccount_withUser() throws Exception {
        mockMvc.perform(get("/user/deletemypmb").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void test_deleteUserAccount_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/deletemypmb").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getAccountForm_withAdmin() throws Exception {
        mockMvc.perform(get("/user/accountform").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void test_getAccountForm_withUser() throws Exception {
        mockMvc.perform(get("/user/accountform").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"));
    }
    @Test
    void test_getAccountForm_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/accountform").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    //@Rollback
    void test_saveBankAccount_withAdmin() throws Exception {
       BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000001");
        bankAccount.setBic("AAAAAAAAAAB");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    //@Rollback
    void test_saveBankAccount_withUser() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000002");
        bankAccount.setBic("AAAAAAAAAAC");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("tram.long@takatoukite.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    //@Rollback
    void test_saveBankAccount_withAnonymous() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000003");
        bankAccount.setBic("AAAAAAAAAAD");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(anonymous()).flashAttr("account",bankAccount))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_deleteBankAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR0000000000000000000000002").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void test_deleteBankAccount_withUser() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR0000000000000000000000003").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    void test_deleteBankAccount_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR0000000000000000000000002").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

/*    @Test
    //@Rollback
    void test_addContact_withAdmin() throws Exception {
        User user = new User();
        user.setUserID(15);
        user.setFirstName("Jeannot");
        user.setLastName("Michel");
        user.setPassword("12345");
        user.setUserRole("ROLE_USER");
        user.setEmail("jean.michel15@mail.fr");
        user.setAddress("rue du test");
        user.setZip(45000);
        user.setCity("test");
        user.setPhone(0000000000);
        user.setDeleted(false);
        String email = "z.z@jetmail.fr";

        mockMvc.perform(post("/user/addcontact").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("email",email))
                .andExpect(status().is3xxRedirection());
    }

    @Test
        //@Rollback
    void test_addContact_withUser() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000002");
        bankAccount.setBic("AAAAAAAAAAC");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("tram.long@takatoukite.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }
    @Test
        //@Rollback
    void test_addContact_withAnonymous() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000003");
        bankAccount.setBic("AAAAAAAAAAD");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(anonymous()).flashAttr("account",bankAccount))
                .andExpect(status().isUnauthorized());
    }*/

}
