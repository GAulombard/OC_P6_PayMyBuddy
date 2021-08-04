package com.openclassrooms.paymybuddy.util;

import com.openclassrooms.paymybuddy.model.Fee;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountUtilTest {

    private BankAccountUtil bankAccountUtil;

    @BeforeEach
    void setUpBeforeTest() {
        bankAccountUtil = new BankAccountUtil();
    }

    @Test
    public void test_getRandomValueBetween(){

        int min = -10;
        int max = 10;
        int result = bankAccountUtil.getRandomValueBetween(min,max);

        assertTrue(max >= result);
        assertTrue(min <= result);

    }

    @Test
    public void test_getRandomValueBetween_shouldThrowIllegalArgumentException_whenMaxIsLowerThanMin(){

        int min = 10;
        int max = -10;

        assertThrows(IllegalArgumentException.class,()->bankAccountUtil.getRandomValueBetween(min,max));

    }

    @Test
    public void test_balanceCalculator(){
        double result = 0;
        double fee = 2.0;
        double accountBalance = 100;
        double transferAmount = 100;

        result = bankAccountUtil.balanceCalculator(accountBalance,fee,transferAmount);

        assertEquals(-2.0,result,0.0);
    }

    @Test
    public void test_isSufficientFound(){
        double accountBalance = 100;
        double amountTransaction = 50;

        assertTrue(bankAccountUtil.isSufficientFound(accountBalance,amountTransaction));

    }

    @Test
    public void test_isSufficientFound_shouldReturnFalse(){
        double accountBalance = 100;
        double amountTransaction = 150;

        assertFalse(bankAccountUtil.isSufficientFound(accountBalance,amountTransaction));

    }

}
