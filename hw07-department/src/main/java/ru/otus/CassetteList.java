package ru.otus;

import java.util.List;

public class CassetteList {
    private List<Cassette> cassetteList;

    private CassetteList(CassetteListBuilder cassetteListBuilder) {
        this.cassetteList = cassetteListBuilder.cassetteListBuilder;
    }

    public static class CassetteListBuilder{
        private List<Cassette> cassetteListBuilder;

        CassetteListBuilder(BankNote bankNote, int amount){
            cassetteListBuilder.add(new Cassette(bankNote, amount));
        }

        CassetteListBuilder addCassette(BankNote bankNote, int amount){
            cassetteListBuilder.add(new Cassette(bankNote, amount));
            return this;
        }

        CassetteList build(){
            return new CassetteList(this);
        }
    }



}
