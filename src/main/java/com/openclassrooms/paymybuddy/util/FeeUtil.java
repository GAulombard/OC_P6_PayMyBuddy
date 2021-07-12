package com.openclassrooms.paymybuddy.util;

/**
 * The type Fee util.
 */
public class FeeUtil {

    /**
     * Fee calculator double.
     *
     * @param rate   the rate
     * @param amount the amount
     * @return the double
     */
    public static double FeeCalculator(double rate, double amount) {

        double result = (amount * rate) / 100;

        return result;
    }

}
