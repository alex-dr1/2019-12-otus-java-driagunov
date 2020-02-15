package ru.otus;

public class CassetteOperation {
    public long put(long amount, long putAmount){
        if (putAmount < 0){
            throw new RuntimeException("Нельзя положить отрицательное число купюр");
        }
        if (putAmount >= (Long.MAX_VALUE - amount)) {
            throw new RuntimeException("Нет места под это число купюр");
        }
        return amount + putAmount;
    }

    public long get(long amount, long getAmount){
        if (getAmount <= 0){
            throw new RuntimeException("Нельзя снять 0 или отрицательное число купюр");
        }
        if (getAmount > amount) {
            throw new RuntimeException("Нельзя снять больше чем есть");
        }
        return amount - getAmount;
    }
}
