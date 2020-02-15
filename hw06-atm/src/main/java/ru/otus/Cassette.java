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
        amount = new CassetteOperation().put(amount, putAmount);
    }

    public void getMoney(long getAmount){
        amount = new CassetteOperation().get(amount, getAmount);
    }
}
