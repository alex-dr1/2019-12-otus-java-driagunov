package ru.otus;

public enum BankNote {
    R5000(5000),
    R2000(2000),
    R1000(1000),
    R500(500),
    R200(200),
    R100(100);

    private final int rating;
    BankNote(int rating) {
        this.rating = rating;
    }

    int getRating(){
        return rating;
    }

}
