package ru.otus;

import java.util.Comparator;

public class CassetteSort implements Comparator<Cassette> {

    @Override
    public int compare(Cassette cassette1, Cassette cassette2) {
        int r1 = cassette1.getBankNote().getRating();
        int r2 = cassette2.getBankNote().getRating();
        if(r1==r2)
            return 0;
        return r1 < r2 ? 1 : -1;
    }
}
