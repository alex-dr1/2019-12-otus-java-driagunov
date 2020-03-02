package ru.otus;

import java.util.Set;
import java.util.TreeSet;

public class SnapshotInit {
    private Set<ATM> atmSetInit = new TreeSet<>(new ATMSort());

    public void save(Set<ATM> atmSet){
        for (ATM atm : atmSet) {
            atmSetInit.add(atm.clone());
        }
    }

    public void showSnapshotInit(){
        System.out.println("<<--Snapshot-->>");
        atmSetInit.forEach(System.out::println);
    }

    public Set<ATM> resetATM(){
        Set<ATM> atmSetInitReset = new TreeSet<>(new ATMSort());
        for (ATM atm: atmSetInit){
            atmSetInitReset.add(new ATM.ATMBuilder()
                    .nameATM(atm.getNameATM())
                    .addCassette(BankNote.R100, atm.getAmountBankNote(BankNote.R100))
                    .addCassette(BankNote.R200, atm.getAmountBankNote(BankNote.R200))
                    .addCassette(BankNote.R1000, atm.getAmountBankNote(BankNote.R1000))
                    .addCassette(BankNote.R5000, atm.getAmountBankNote(BankNote.R5000))
                    .addCassette(BankNote.R2000, atm.getAmountBankNote(BankNote.R2000))
                    .addCassette(BankNote.R500, atm.getAmountBankNote(BankNote.R500))
                    .build());
        }
        return atmSetInitReset;
    }
}
