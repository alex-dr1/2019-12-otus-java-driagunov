package ru.otus;

import java.util.Comparator;

public class ATMSort implements Comparator<ATM> {
    @Override
    public int compare(ATM atm1, ATM atm2) {
        return atm1.getNameATM().compareTo(atm2.getNameATM());
    }
}
