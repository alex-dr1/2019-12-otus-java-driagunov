package ru.otus;

public class ATMFactory {
    public ATM create(String nameATM, int amountBankNote){
        return new ATM.ATMBuilder()
                .nameATM(nameATM)
                .addCassette(BankNote.R100, amountBankNote)
                .addCassette(BankNote.R200,amountBankNote)
                .addCassette(BankNote.R1000,amountBankNote)
                .addCassette(BankNote.R5000,amountBankNote)
                .addCassette(BankNote.R2000,amountBankNote)
                .addCassette(BankNote.R500,amountBankNote)
                .build();
    }
}
