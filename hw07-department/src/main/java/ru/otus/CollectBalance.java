package ru.otus;

import java.util.Set;

public class CollectBalance {
    public void executeATMBalance(Set<ATM> atmInfoList){
        if (atmInfoList.isEmpty()){
            System.out.println("Add ATM");
            return;
        }
        atmInfoList.stream().map(atm -> "Balance " + atm.getNameATM() + ": " + atm.getBalance()).forEach(System.out::println);
    }
}
