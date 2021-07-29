package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class FeeServiceTest {

    @Mock
    private FeeRepository feeRepository;

    @InjectMocks
    private FeeService feeService;

    private Fee fee;

    @BeforeEach
    void setUpBeforeTest() {
        fee = new Fee();
    }

    @Test
    public void test_getFees(){
        Fee fee = new Fee();
        Fee fee2 = new Fee();
        Fee fee3 = new Fee();

        List<Fee> fees = Arrays.asList(fee, fee2, fee3);

        when(feeService.getFees()).thenReturn(fees);

        List<Fee> feeList = (List<Fee>) feeService.getFees();

        assertEquals(3, feeList.size());
    }

    @Test
    public void test_saveFee(){

        fee = new Fee();

        when(feeRepository.save(fee)).thenReturn(fee);

        feeService.saveFee(fee);

        verify(feeRepository).save(fee);

    }
}
