package com.openclassrooms.paymybuddy.util;

public class FeeUtil {

    public static double FeeCalculator(double rate, double amount) {

        double result = (amount * rate) / 100;

        return result;
    }

}
