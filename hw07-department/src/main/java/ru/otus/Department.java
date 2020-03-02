package ru.otus;

import java.util.Set;
import java.util.TreeSet;

public class Department {
    private final String nameDepartment;
    private Set<ATM> atmSet = new TreeSet<>(new ATMSort());
    private final SnapshotInit snapshotInit = new SnapshotInit();

    public Department(String nameDepartment, int amountATM) {
        this.nameDepartment = nameDepartment;

        ATMFactory atmFactory = new ATMFactory();
        for (int i = 1; i <= amountATM; i++){
            String nameATM = "ATM" + i;
            atmSet.add(atmFactory.create(nameATM, i*10));
        }
        snapshotInit.save(atmSet);
    }

    public void showDepartment() {
        System.out.println("Департамент: " + nameDepartment);
        atmSet.forEach(System.out::println);
    }

    public void showATMBalance(){
        CollectBalance collectBalance = new CollectBalance();
        collectBalance.executeATMBalance(atmSet);
    }

    public void requiredAllATM (int amount){
        System.out.println("Снятие с банкоматов суммы: " + amount);
        atmSet.forEach(atm -> atm.requiredAmount(amount));
    }

    public void reset(){
        System.out.println(">>--RESET--<<");
        snapshotInit.showSnapshotInit();
        atmSet = snapshotInit.resetATM();
    }

}
