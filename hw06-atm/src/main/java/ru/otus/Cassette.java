package ru.otus;


public class Cassette {
    private long amount;
    private final BankNote bankNote;

    public Cassette(BankNote bankNote, long amount) {
        if (amount < 0)
            throw new RuntimeException("Нельзя создать кассету с отрицательным числом купюр");
        this.amount = amount;
        this.bankNote = bankNote;
    }

    public long getAmount() {
        return amount;
    }

    public BankNote getBankNote() {
        return bankNote;
    }

    public void putMoney(long putAmount){
        if (putAmount < 0){
            throw new RuntimeException("Нельзя положить отрицательное число купюр");
        }
        if (putAmount >= (Long.MAX_VALUE - amount)) {
            throw new RuntimeException("Нет места под это число купюр");
        }
        amount = amount + putAmount;
    }

    public void requiredMoney(long getAmount){
        if (getAmount <= 0){
            throw new RuntimeException("Нельзя снять 0 или отрицательное число купюр");
        }
        if (getAmount > amount) {
            throw new RuntimeException("Нельзя снять больше чем есть");
        }
        amount = amount - getAmount;
    }
}
