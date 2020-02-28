package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ATM {
    // TODO Builder atm added
    private final String nameATM;
    private final Set<Cassette> cassetteSet;

    public String getNameATM() {
        return nameATM;
    }

    public ATM(String nameATM, Set<Cassette> cassetteSet) {
        this.nameATM = nameATM;
        this.cassetteSet = cassetteSet;
    }
    //================================================

    public Map<BankNote, Integer> requiredAmount(int getMoneyAmount) {

        Map<BankNote, Integer> result = new HashMap<>();

        if (getBalance() < getMoneyAmount){
            throw new RuntimeException("Баланс меньше запрошенной суммы");
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
    private int getAmountBankNote(BankNote bankNote){
        int result = 0;
        for (Cassette cassette: cassetteSet){
            if(cassette.getBankNote() == bankNote){
                result += cassette.getAmount();
            }
        }
        return result;
    }

    // баланс в банкомате
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
        result = new StringBuilder("=======================\n");
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
}
