package com.openclassrooms.paymybuddy.util;

import com.openclassrooms.paymybuddy.model.Fee;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class FeeUtilTest {


    @BeforeEach
    void setupBeforeEach(){

    }

    @Test
    public void test_feeCalculator(){
        double rate = 2.0;
        double amount = 100.0;

        double result = FeeUtil.FeeCalculator(rate,amount);

        assertEquals(2.0,result,0.0);
    }
}
