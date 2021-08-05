package com.openclassrooms.paymybuddy.model;

import java.util.Random;

/**
 * The type Bank.
 */
public class Bank {

    /**
     * The enum Bank name.
     */
    public enum BankName {
        /**
         * Postal bank bank name.
         */
        POSTAL_BANK,
        /**
         * Agricultural credit bank name.
         */
        AGRICULTURAL_CREDIT,
        /**
         * Popular bank bank name.
         */
        POPULAR_BANK,
        /**
         * General society bank name.
         */
        GENERAL_SOCIETY,
        /**
         * Savings bank bank name.
         */
        SAVINGS_BANK;

        /**
         * Gets random bank name.
         *
         * @return the random bank name
         */
        public static BankName getRandomBankName() {
            BankName[] value = BankName.values();
            int length = value.length;
            int randIndex = new Random().nextInt(length);
            return value[randIndex];
        }
    }
}
