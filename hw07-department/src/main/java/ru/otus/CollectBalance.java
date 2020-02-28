package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class CollectBalance {
    private List<ATMBalance> atmBalanceList = new ArrayList<>();

    public void addATM (ATMBalance atmBalance){
        atmBalanceList.add(atmBalance);
    }

    public void clearATMList(){
        atmBalanceList.clear();
    }

    public void executeATMBalance(){
        if (atmBalanceList.isEmpty()){
            System.out.println("Add ATM");
            return;
        }
        atmBalanceList.stream().map(atm -> "Balance " + atm.getNameATM() + ": " + atm.getBalance()).forEach(System.out::println);
    }
}
