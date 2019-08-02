package com.kripesh;


import com.kripesh.service.TransactionInfoService;

public class Main {

    public static void main(String[] args) {
	// write your code here
        TransactionInfoService transactionInfoService = new TransactionInfoService();
        transactionInfoService.readFile("test.csv");
        System.out.println("#Balances \n" + transactionInfoService.getTotalBalanceAllBanks());
        System.out.println("#Highest Balance \n" + transactionInfoService.highestBalance());
        System.out.println("#Frequesntly used account \n" + transactionInfoService.frequentUsedAccount());

    }
}
