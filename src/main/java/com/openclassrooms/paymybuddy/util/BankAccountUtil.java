package com.openclassrooms.paymybuddy.util;

import com.openclassrooms.paymybuddy.model.Bank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class BankAccountUtil {

    public static int getRandomValueBetween(int min,int max){

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    public static String getRandomBankNameFromEnum() {
        return Bank.bankName.getRandomBankName().toString();
    }

}
