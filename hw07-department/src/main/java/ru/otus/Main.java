package ru.otus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {




        //=========================================
        ATM atm;
        String nameATM = "ATM001";

        List<Cassette> cassettes = new ArrayList<>();

        for (BankNote bankNote: BankNote.values()){
            cassettes.add(new Cassette(bankNote, 0));
        }


        atm = new ATM(nameATM, cassettes);

        Map<BankNote,Integer> putMoneyPack1 = new HashMap<>();
        putMoneyPack1.put(BankNote.R200, 0);
        putMoneyPack1.put(BankNote.R100, 0);
        putMoneyPack1.put(BankNote.R500, 2);
        putMoneyPack1.put(BankNote.R1000, 0);
        putMoneyPack1.put(BankNote.R5000, 1);
        atm.putMoneyPack(putMoneyPack1);
        System.out.println("Баланс: " + atm.getBalance());


        System.out.println(atm.requiredAmount(5100));
        System.out.println("Баланс: " + atm.getBalance());


        Map<BankNote,Integer> putMoneyPack2 = new HashMap<>();
        putMoneyPack2.put(BankNote.R200, 3);
        putMoneyPack2.put(BankNote.R100, 0);
        putMoneyPack2.put(BankNote.R500, 6);
        putMoneyPack2.put(BankNote.R5000, 1);
        atm.putMoneyPack(putMoneyPack2);
        System.out.println("Баланс: " + atm.getBalance());

        System.out.println(atm.requiredAmount(6541));
        System.out.println("Баланс: " + atm.getBalance());


    }
}
