package ru.otus;

public class Main {

    public static void main(String[] args) {
        Department department1 = new Department("Roga & Copyta", 3);
        department1.showDepartment();

        runDemo(department1, 11100);

        runDemo(department1, 55500);

        runDemo(department1, 88000);
    }

    private static void runDemo(Department department1, int amount) {
        System.out.println();
        department1.showATMBalance();
        department1.requiredAllATM(amount);
        department1.showATMBalance();
        department1.reset();
    }

}
