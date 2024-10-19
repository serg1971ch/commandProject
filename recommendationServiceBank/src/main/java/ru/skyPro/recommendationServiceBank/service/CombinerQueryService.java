package ru.skyPro.recommendationServiceBank.service;

public interface CombinerQueryService {
    String setQueryUserOfProduct();

    String setQueryActiveUserOfProduct();

    String setQueryTransactionSummaryCompare();

    String setQueryTransactionSumCompareDepositWithdraw();
}
