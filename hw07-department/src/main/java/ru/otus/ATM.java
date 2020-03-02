package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ATM implements ATMInfo {
    private final String nameATM;
    private final Set<Cassette> cassetteSet;

    public String getNameATM() {
        return nameATM;
    }

    private ATM(ATMBuilder atmBuilder) {
        if(atmBuilder.nameATM == null)
            throw new RuntimeException("ATMBuilder: nameATM is null");
        this.nameATM = atmBuilder.nameATM;
        this.cassetteSet = atmBuilder.cassetteSetBuilder;
    }
    //================================================
    // снять какую-то сумму
    public Map<BankNote, Integer> requiredAmount(int getMoneyAmount) {

        Map<BankNote, Integer> result = new HashMap<>();

        if (getBalance() < getMoneyAmount){
            throw new RuntimeException("Баланс " + getBalance() + " меньше запрошенной суммы " + getMoneyAmount);
        }

        for (Cassette cassette : cassetteSet){
            BankNote bankNote = cassette.getBankNote();
            int necessary = getMoneyAmount/bankNote.getRating();
            if((necessary) > 0){
                int thereIs = getAmountBankNote(bankNote);
                if (thereIs > 0){
                    if(thereIs >= necessary){
                        getMoneyAmount = withdrawalProcess(getMoneyAmount, result, bankNote, necessary);
                    } else {
                        getMoneyAmount = withdrawalProcess(getMoneyAmount, result, bankNote, thereIs);
                    }
                }
            }
        }
        return result;
    }
    private int withdrawalProcess(int moneyAmount, Map<BankNote, Integer> result, BankNote bankNote, int sum) {
        moneyAmount = moneyAmount - sum*bankNote.getRating();
        requiredBankNote(bankNote, sum);
        result.put(bankNote, sum);
        return moneyAmount;
    }

    // снять такие-то бакноты в таком-то количестве
    private void requiredBankNote(BankNote bankNote, long getAmount){
        for (Cassette cassette: cassetteSet){
            if(cassette.getBankNote() == bankNote){
                cassette.requiredMoney(getAmount);
            }
        }
    }

    //================================================
    // кол-во банкнот в банкомате
    int getAmountBankNote(BankNote bankNote){
        int result = 0;
        for (Cassette cassette: cassetteSet){
            if(cassette.getBankNote() == bankNote){
                result += cassette.getAmount();
            }
        }
        return result;
    }

    // баланс в банкомате
    @Override
    public long getBalance(){
        long result = 0;
        for (BankNote bankNote: BankNote.values()){
            result += getAmountBankNote(bankNote)*bankNote.getRating();
        }
        return result;
    }


    //=====================================================
    // Положить пачку денег
    public void putMoneyPack(Map<BankNote, Integer> mp){
        mp.forEach(this::putBankNote);
    }

    private void putBankNote(BankNote bankNote, long putAmount){
        for (Cassette cell: cassetteSet){
            if(cell.getBankNote() == bankNote){
                cell.putMoney(putAmount);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder result;
        result = new StringBuilder();
        result.append("Банкомат: ").append(getNameATM()).append("\n");
        result.append("  Баланс: ").append(getBalance()).append("\n");
        result.append("  Купюры: {");

        for (Cassette cassette: cassetteSet){
            BankNote bankNote = cassette.getBankNote();
            long amount = getAmountBankNote(bankNote);
            if (amount > 0){
                result.append(bankNote).append("-").append(amount).append("шт, ");
            }
        }

        result = new StringBuilder(String.copyValueOf(result.toString().toCharArray(), 0, result.length() - 2));
        result.append("}\n");

        return result.toString();
    }

    public ATM clone(){
        return new ATM.ATMBuilder()
                .nameATM(nameATM)
                .addCassette(BankNote.R100,  getAmountBankNote(BankNote.R100))
                .addCassette(BankNote.R200,  getAmountBankNote(BankNote.R200))
                .addCassette(BankNote.R1000, getAmountBankNote(BankNote.R1000))
                .addCassette(BankNote.R5000, getAmountBankNote(BankNote.R5000))
                .addCassette(BankNote.R2000, getAmountBankNote(BankNote.R2000))
                .addCassette(BankNote.R500,  getAmountBankNote(BankNote.R500))
                .build();
    }

    public static class ATMBuilder{
        private String nameATM;
        private Set<Cassette> cassetteSetBuilder = new TreeSet<>(new CassetteSort());

        ATMBuilder nameATM (String nameATM){
            this.nameATM = nameATM;
            return this;
        }

        ATMBuilder addCassette(BankNote bankNote, int amount){
            cassetteSetBuilder.add(new Cassette(bankNote, amount));
            return this;
        }

        ATM build(){
            return new ATM(this);
        }
    }
}
