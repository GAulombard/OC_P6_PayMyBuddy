package com.openclassrooms.paymybuddy.model;

import java.util.Random;

public class Bank {

    public enum bankName {
        POSTAL_BANK, AGRICULTURAL_CREDIT, POPULAR_BANK, GENERAL_SOCIETY, SAVINGS_BANK;

        public static bankName getRandomBankName() {
            bankName[] value = bankName.values();
            int length = value.length;
            int randIndex = new Random().nextInt(length);
            return value[randIndex];
        }
    }
}
