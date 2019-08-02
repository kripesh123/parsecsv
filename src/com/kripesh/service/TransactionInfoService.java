package com.kripesh.service;

import com.kripesh.domain.TransactionInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionInfoService {
    private List<TransactionInfo> transactionInfo;
    private Map<Integer, Double> accountTransactionAmount;

    public TransactionInfoService() {
        transactionInfo = new ArrayList<>();
        accountTransactionAmount = new LinkedHashMap<>();
    }

    /**
     * @return The final balances of all bank accounts
     */
    public Map<Integer, Double> getTotalBalanceAllBanks() {
        transactionInfo.forEach(e-> {
            Integer sourceAccount =e.getSourceAcc();
            Integer destinationAccount = e.getDestinationAcc();
            double amount = e.getAmount();
            if (sourceAccount == 0) {
                if (!accountTransactionAmount.containsKey(destinationAccount)) {
                    accountTransactionAmount.put(destinationAccount, amount);
                }else{
                    accountTransactionAmount.replace(destinationAccount,
                            accountTransactionAmount.get(destinationAccount) + amount);
                }
            }else{
                if(!accountTransactionAmount.containsKey(sourceAccount)) {
                    accountTransactionAmount.put(sourceAccount, amount);
                }else{
                    accountTransactionAmount.replace(sourceAccount, accountTransactionAmount.get(sourceAccount) - amount);
                }

                if(!accountTransactionAmount.containsKey(destinationAccount)) {
                    accountTransactionAmount.put(destinationAccount, amount);
                }else{
                    accountTransactionAmount.replace(destinationAccount, accountTransactionAmount.get(destinationAccount) + amount);
                }

            }
        });

        return accountTransactionAmount;
    }

    /**
     * @return The bank accounts with the highest final balance
     */
    public int highestBalance() {
        if (!accountTransactionAmount.isEmpty()) {
            return Collections.max(accountTransactionAmount.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
        }
        return -1;
    }

    /**
     * @return The most frequently used bank account as a Source
     */
    public long frequentUsedAccount() {
        Map<Integer, Long> counters = transactionInfo.stream()
                .collect(Collectors.groupingBy(TransactionInfo::getSourceAcc, Collectors.counting()));
        Optional<Map.Entry<Integer, Long>> count = counters.entrySet().stream()
                .filter(e -> e.getKey() != 0)
                .max(Map.Entry.comparingByValue());
        return count.isPresent() ? count.get().getKey() : -1;

    }

    /**
     *
     * @param fileLocation file Location path
     */
    public void readFile(String fileLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            // Ignore this header line
            reader.readLine();

            String line;
            while((line = reader.readLine()) != null){
                String[] lines = line.split(",");
                int sourceAccount = Integer.parseInt(lines[0]);
                int destinationAccount = Integer.parseInt(lines[1]);
                double amount = Double.parseDouble(lines[2]);
                String date = lines[3];
                int transferId = Integer.parseInt(lines[4]);
                transactionInfo.add(new TransactionInfo(sourceAccount, destinationAccount, amount, date, transferId));
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
