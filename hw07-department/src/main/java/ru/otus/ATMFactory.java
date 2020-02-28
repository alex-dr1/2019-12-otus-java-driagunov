package ru.otus;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ATMFactory {
    private String nameATM;
    private Set<Cassette> cassetteSet;

    public ATMFactory(ATMBuilder atmBuilder) {
        this.nameATM = atmBuilder.nameATM;
        this.cassetteSet = atmBuilder.cassetteSetBuilder;
    }

    public ATM create(){
        if (nameATM.isEmpty())
            throw new RuntimeException("name ATM is empty");
        return new ATM(nameATM, cassetteSet);
    }

    public static class ATMBuilder{
        private String nameATM;
        private Set<Cassette> cassetteSetBuilder = new TreeSet<>(new CassetteSort());

        ATMBuilder(){
        }

        ATMBuilder nameATM (String nameATM){
            this.nameATM = nameATM;
            return this;
        }

        ATMBuilder addCassette(BankNote bankNote, int amount){
            cassetteSetBuilder.add(new Cassette(bankNote, amount));
            return this;
        }

        ATMFactory build(){
            return new ATMFactory(this);
        }
    }
}
