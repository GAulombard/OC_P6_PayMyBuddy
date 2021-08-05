package com.openclassrooms.paymybuddy.util;

import com.openclassrooms.paymybuddy.model.Bank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The type Bank account util.
 */
public class BankAccountUtil {

    /**
     * Get random value between int.
     *
     * @param min the min
     * @param max the max
     * @return the int
     */
    public static int getRandomValueBetween(int min,int max){

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Gets random bank name from enum.
     *
     * @return the random bank name from enum
     */
    public static String getRandomBankNameFromEnum() {
        return Bank.BankName.getRandomBankName().toString();
    }

    /**
     * Balance calculator double.
     *
     * @param accountBalance the account balance
     * @param feeAmount      the fee amount
     * @param transferAmount the transfer amount
     * @return the double
     */
    public static double balanceCalculator(double accountBalance, double feeAmount, double transferAmount) {
        double result = 0;

        result = accountBalance - feeAmount - transferAmount;

        return result;
    }

    /**
     * Is sufficient found boolean.
     *
     * @param accountBalance    the account balance
     * @param amountTransaction the amount transaction
     * @return the boolean
     */
    public static boolean isSufficientFound(double accountBalance, double amountTransaction) {
        if ( accountBalance >= amountTransaction) return true;
        else return false;
    }

}
