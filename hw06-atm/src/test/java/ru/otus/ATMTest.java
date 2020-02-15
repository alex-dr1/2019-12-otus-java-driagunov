package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    ATM atm;
    List<Cassette> cassettes = new ArrayList<>();
    Map<BankNote,Integer> putMoneyPack1 = new HashMap<>();


    @BeforeEach
    void setUp(){
        for (BankNote bankNote: BankNote.values()){
             cassettes.add(new Cassette(bankNote, 0));
        }
        atm = new ATM(cassettes);

        putMoneyPack1.put(BankNote.R200, 5);
        putMoneyPack1.put(BankNote.R100, 10);
        putMoneyPack1.put(BankNote.R500, 4);
        putMoneyPack1.put(BankNote.R1000, 6);
        atm.putMoneyPack(putMoneyPack1);
    }

    @Test
    void getMoneyPack() {
        //{R1000=3, R100=1, R500=1}
        Map<BankNote, Integer> getMoneyPack = new HashMap<>();
        getMoneyPack.put(BankNote.R1000, 3);
        getMoneyPack.put(BankNote.R100, 1);
        getMoneyPack.put(BankNote.R500, 1);

        assertEquals(getMoneyPack, atm.getMoneyPack(3600));
        assertEquals(6_400L, atm.getBalance());
        assertThrows(RuntimeException.class, ()->atm.getMoneyPack(100000));
    }

    @Test
    void getBalance() {
        assertEquals(10_000L, atm.getBalance());
    }

    @Test
    void putMoneyPack() {
        Map<BankNote, Integer> putMoneyPack = new HashMap<>();
        putMoneyPack.put(BankNote.R1000, 8);
        putMoneyPack.put(BankNote.R100, 10);
        putMoneyPack.put(BankNote.R500, 2);
        atm.putMoneyPack(putMoneyPack);
        assertEquals(20_000L, atm.getBalance());

        Map<BankNote, Integer> putMoneyPack2 = new HashMap<>();
        putMoneyPack2.put(BankNote.R1000, -8);
        putMoneyPack2.put(BankNote.R100, 10);
        putMoneyPack2.put(BankNote.R500, 2);
        assertThrows(RuntimeException.class, ()->atm.putMoneyPack(putMoneyPack2));
    }
}