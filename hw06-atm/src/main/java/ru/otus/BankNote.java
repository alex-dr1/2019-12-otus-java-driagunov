package ru.otus;

public enum BankNote {
    R5000(5000L),
    R2000(2000L),
    R1000(1000L),
    R500(500L),
    R200(200L),
    R100(100L);

    private final long rating;
    BankNote(long rating) {
        this.rating = rating;
    }

    long getRating(){
        return rating;
    }

}
