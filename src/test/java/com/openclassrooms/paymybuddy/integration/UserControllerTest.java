package com.openclassrooms.paymybuddy.integration;

import com.openclassrooms.paymybuddy.exception.ContactException;
import com.openclassrooms.paymybuddy.exception.InsufficientFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.ContactService;
import com.openclassrooms.paymybuddy.service.MyUserDetailsService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ContactService contactService;

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
    @Rollback
    @Transactional
    void test_deleteUserAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/user/deletemypmb").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteUserAccount_withUser() throws Exception {
        mockMvc.perform(get("/user/deletemypmb").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @Rollback
    @Transactional
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
    @Rollback
    @Transactional
    void test_saveBankAccount_withAdmin() throws Exception {
       BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000001");
        bankAccount.setBic("AAAAAAAAAAB");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveBankAccount_withAdmin_withFieldError() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR000000000000000000000001");
        bankAccount.setBic("AAAAAAAAAB");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveBankAccount_withUser() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000002");
        bankAccount.setBic("AAAAAAAAAAC");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("tram.long@takatoukite.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @Rollback
    @Transactional
    void test_saveBankAccount_withAnonymous() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000003");
        bankAccount.setBic("AAAAAAAAAAD");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(anonymous()).flashAttr("account",bankAccount))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteBankAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR123456789123456B789123456").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteBankAccount_withUser() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR12345421V123456B789123456").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @Rollback
    @Transactional
    void test_deleteBankAccount_withAnonymous() throws Exception {
        mockMvc.perform(get("/user/deleteaccount?id=FR12345421V123456B789123456").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    /*
    @Test
    @Rollback
    @Transactional
    void test_addContact_withAdmin() throws Exception {
        User user = new User();
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


        userRepository.save(user);

        mockMvc.perform(post("/user/addcontact").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("email",user.getEmail()))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @Rollback
    @Transactional
    void test_addContact_withUser() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000002");
        bankAccount.setBic("AAAAAAAAAAC");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(httpBasic("tram.long@takatoukite.fr","123456789")).flashAttr("account",bankAccount))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    @Rollback
    @Transactional
    void test_addContact_withAnonymous() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("FR0000000000000000000000003");
        bankAccount.setBic("AAAAAAAAAAD");
        bankAccount.setDeleted(false);

        mockMvc.perform(post("/user/createaccount").with(anonymous()).flashAttr("account",bankAccount))
                .andExpect(status().isUnauthorized());
    }*/

    @Test
    @Rollback
    @Transactional
    void test_deleteContact_withAdmin() throws Exception, ContactException {
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername("g.aulomb@jetmail.fr");
        User contact = userService.findUserByEmail("tram.long@takatoukite.fr");

        contactService.saveContactRelationship(user,contact);

        mockMvc.perform(get("/user/delete-contact?id=5").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteContact_withUser() throws Exception, ContactException {
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername("tram.long@takatoukite.fr");
        User contact = userService.findUserByEmail("g.aulomb@jetmail.fr");

        contactService.saveContactRelationship(user,contact);

        mockMvc.perform(get("/user/delete-contact?id=5").with(httpBasic("tram.long@takatoukite.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteContact_withAnonymous() throws Exception, ContactException {
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername("tram.long@takatoukite.fr");
        User contact = userService.findUserByEmail("g.aulomb@jetmail.fr");

        contactService.saveContactRelationship(user,contact);

        mockMvc.perform(get("/user/delete-contact?id=5").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveTransaction_withAdmin() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction.setDebtor(bankAccountRepository.getById("FR1234B6789127856B789123456"));
        transaction.setCreditor(bankAccountRepository.getById("FR123456789123456B789123456"));
        transaction.setAmount(100);
        transaction.setMessage("test");


        mockMvc.perform(post("/user/new-transfer").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("transaction",transaction))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveTransaction_withAdmin_withFieldError() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction.setDebtor(bankAccountRepository.getById("FR1234B6789127856B789123456"));
        transaction.setCreditor(bankAccountRepository.getById("FR123456789123456B789123456"));
        transaction.setAmount(-100);
        transaction.setMessage("test");


        mockMvc.perform(post("/user/new-transfer").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("transaction",transaction))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveTransaction_withUser() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction.setDebtor(bankAccountRepository.getById("FR1234B6789127856B789123456"));
        transaction.setCreditor(bankAccountRepository.getById("FR123456789123456B789123456"));
        transaction.setAmount(100);
        transaction.setMessage("test");


        mockMvc.perform(post("/user/new-transfer").with(httpBasic("tram.long@takatoukite.fr","123456789")).flashAttr("transaction",transaction))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveTransaction_withAnonymous() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction.setDebtor(bankAccountRepository.getById("FR1234B6789127856B789123456"));
        transaction.setCreditor(bankAccountRepository.getById("FR123456789123456B789123456"));
        transaction.setAmount(100);
        transaction.setMessage("test");


        mockMvc.perform(post("/user/new-transfer").with(anonymous()).flashAttr("transaction",transaction))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_saveTransaction_shouldThrowInsufficientFoundException() throws Exception, InsufficientFoundException {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.parse("2021-12-15T15:14:21.629"));
        transaction.setDebtor(bankAccountRepository.getById("FR12345848912A456B789123456"));
        transaction.setCreditor(bankAccountRepository.getById("FR123456789123456B789123456"));
        transaction.setAmount(100);
        transaction.setMessage("test");


        mockMvc.perform(post("/user/new-transfer").with(httpBasic("g.aulomb@jetmail.fr","123456789")).flashAttr("transaction",transaction))
                .andExpect(status().isInternalServerError());
    }

}
