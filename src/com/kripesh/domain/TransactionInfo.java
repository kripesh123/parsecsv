package com.kripesh.domain;

public class TransactionInfo {

    private int sourceAcc;
    private int destinationAcc;
    private double amount;
    private String data;
    private int transferId;

    public TransactionInfo(int sourceAcc, int destinationAcc, double amount, String data, int transferId) {
        this.sourceAcc = sourceAcc;
        this.destinationAcc = destinationAcc;
        this.amount = amount;
        this.data = data;
        this.transferId = transferId;
    }

    public int getSourceAcc() {
        return sourceAcc;
    }

    public int getDestinationAcc() {
        return destinationAcc;
    }

    public double getAmount() {
        return amount;
    }

}
