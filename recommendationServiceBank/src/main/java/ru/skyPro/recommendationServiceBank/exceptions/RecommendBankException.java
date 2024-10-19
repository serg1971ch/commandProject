package ru.skyPro.recommendationServiceBank.exceptions;

public class RecommendBankException extends RuntimeException {
    public RecommendBankException() {
        super("That user does not have recommend from bank");
    }
}
