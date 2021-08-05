package com.openclassrooms.paymybuddy.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminControllerTest {

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
    void test_getFullDashBoard_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getFullDashBoard_withUser() throws Exception {
        mockMvc.perform(get("/admin").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getFullDashBoard_withAdmin() throws Exception {
        mockMvc.perform(get("/admin").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users","accounts","transactions","fees"));
    }

    @Test
    void test_getAllUsers_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/users").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void test_getAllUsers_withUser() throws Exception {
        mockMvc.perform(get("/admin/users").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getAllUsers_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/users").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getAllBankAccounts_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/accounts").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accounts"));
    }

    @Test
    void test_getAllBankAccounts_withUser() throws Exception {
        mockMvc.perform(get("/admin/accounts").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getAllBankAccounts_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/accounts").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getAllTransactions_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/transactions").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions"));
    }

    @Test
    void test_getAllTransactions_withUser() throws Exception {
        mockMvc.perform(get("/admin/transactions").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getAllTransactions_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/transactions").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void test_getAllFees_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/fees").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("fees"));
    }

    @Test
    void test_getAllFees_withUser() throws Exception {
        mockMvc.perform(get("/admin/fees").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getAllFees_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/fees").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteUser_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/delete?id=4").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteUser_withUser() throws Exception {
        mockMvc.perform(get("/admin/delete?id=4").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteUser_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/delete?id=4").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeAdmin_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/make-admin?id=4").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeAdmin_withUser() throws Exception {
        mockMvc.perform(get("/admin/make-admin?id=4").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeAdmin_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/make-admin?id=4").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeUser_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/make-user?id=4").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeUser_withUser() throws Exception {
        mockMvc.perform(get("/admin/make-user?id=4").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_makeUser_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/make-user?id=4").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverUser_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/user-recovery?id=4").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverUser_withUser() throws Exception {
        mockMvc.perform(get("/admin/user-recovery?id=4").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverUser_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/user-recovery?id=4").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/delete-account?id=FR12345421V123456B789123456").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteAccount_withUser() throws Exception {
        mockMvc.perform(get("/admin/delete-account?id=FR12345421V123456B789123456").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_deleteAccount_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/delete-account?id=FR12345421V123456B789123456").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverAccount_withAdmin() throws Exception {
        mockMvc.perform(get("/admin/account-recovery?id=FR1234B6789127856B789123456").with(httpBasic("g.aulomb@jetmail.fr","123456789")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverAccount_withUser() throws Exception {
        mockMvc.perform(get("/admin/account-recovery?id=FR1234B6789127856B789123456").with(httpBasic("j.bombeurre@jetmail.fr","123456789")))
                .andExpect(status().isForbidden());
    }

    @Test
    @Rollback
    @Transactional
    void test_recoverAccount_withAnonymous() throws Exception {
        mockMvc.perform(get("/admin/account-recovery?id=FR1234B6789127856B789123456").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }


}
