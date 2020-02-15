package ru.otus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ATM atm;

        List<Cassette> cassettes = new ArrayList<>();

        for (BankNote bankNote: BankNote.values()){
            cassettes.add(new Cassette(bankNote, 0));
        }

        atm = new ATM(cassettes);

        Map<BankNote,Integer> putMoneyPack1 = new HashMap<>();
        putMoneyPack1.put(BankNote.R200, 5);
        putMoneyPack1.put(BankNote.R100, 10);
        putMoneyPack1.put(BankNote.R500, 4);
        putMoneyPack1.put(BankNote.R1000, 6);
        atm.putMoneyPack(putMoneyPack1);
        System.out.println("Баланс: " + atm.getBalance());


        System.out.println(atm.getMoneyPack(3600));
        System.out.println("Баланс: " + atm.getBalance());


        Map<BankNote,Integer> putMoneyPack2 = new HashMap<>();
        putMoneyPack2.put(BankNote.R200, 3);
        putMoneyPack2.put(BankNote.R100, 0);
        putMoneyPack2.put(BankNote.R500, 6);
        putMoneyPack2.put(BankNote.R5000, 1);
        atm.putMoneyPack(putMoneyPack2);
        System.out.println("Баланс: " + atm.getBalance());

        System.out.println(atm.getMoneyPack(6541));
        System.out.println("Баланс: " + atm.getBalance());


    }
}
