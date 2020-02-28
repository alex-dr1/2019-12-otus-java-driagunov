package ru.otus;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Department {
    private final String nameDepartment;
    private final Set<ATM> atmSet = new TreeSet<>(new ATMSort());;

    public Department(String nameDepartment, int amountATM) {
        this.nameDepartment = nameDepartment;

        ATMFactory atmFactory = new ATMFactory();
        for (int i = 1; i <= 3; i++){
            String nameATM = "ATM" + i;
            atmSet.add(atmFactory.create(nameATM, i*10));
        }
    }

    public void showDepartment() {
        System.out.println("Департамент: " + nameDepartment);
        atmSet.forEach(System.out::println);
    }
}
