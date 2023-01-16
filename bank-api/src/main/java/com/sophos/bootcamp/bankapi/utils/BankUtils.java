package com.sophos.bootcamp.bankapi.utils;

import com.sophos.bootcamp.bankapi.entities.enums.AccountType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.CHECKING;
import static com.sophos.bootcamp.bankapi.entities.enums.AccountType.SAVINGS;

public class BankUtils {

    public static Double AVAILABLE_BALANCE_CHECKING_ACCOUNT = 3000000d;

    public static Double getGmfCalculator(Double transactionAmount, Boolean gmfExempt) {
        if (gmfExempt == false) {
            double gmf = transactionAmount / 1000 * 4;
            return gmf + transactionAmount;
        } else return transactionAmount;
    }

    public static Double getAvailableBalance(Double balance, Boolean gmfExempt, AccountType accountType) {
            if(CHECKING.equals(accountType) && gmfExempt == false){
                double gmf = (AVAILABLE_BALANCE_CHECKING_ACCOUNT + balance) / 1000 * 4;
                return AVAILABLE_BALANCE_CHECKING_ACCOUNT + balance - gmf;
            } else if (CHECKING.equals(accountType) && gmfExempt == true){
                double availableBalance = AVAILABLE_BALANCE_CHECKING_ACCOUNT + balance;
                return availableBalance;
            } else if (SAVINGS.equals(accountType) && gmfExempt == false) {
                double gmf = balance / 1000 * 4;
                return balance - gmf;
            } else if (SAVINGS.equals(accountType) && gmfExempt == true){
                return balance;
            }
            return balance;
    }

    public static Boolean validateEmailAddress(String email) {
        String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}

