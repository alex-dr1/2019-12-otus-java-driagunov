package ru.otus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM {

    private final String nameATM;
    private final List<Cassette> cassetteList;

    public String getNameATM() {
        return nameATM;
    }

    public ATM(String nameATM, List<Cassette> cassetteList) {
        this.nameATM = nameATM;
        this.cassetteList = cassetteList;
    }
    //================================================

    public Map<BankNote, Integer> requiredAmount(int getMoneyAmount) {
        Map<BankNote, Integer> result = new HashMap<>();
        if (getBalance() < getMoneyAmount){
            throw new RuntimeException("Баланс меньше запрошенной суммы");
        }
        for (Cassette cassette: cassetteList){
            BankNote bankNote = cassette.getBankNote();
            int dev = getMoneyAmount/bankNote.getRating();
            if((dev) > 0){
                if ((getAmountBankNote(bankNote)) > 0){
                    getMoneyAmount = getMoneyAmount - dev*bankNote.getRating();
                    requiredBankNote(bankNote, dev);
                    result.put(bankNote, dev);
                }
            }
        }
        return result;
    }

    // снять такие-то бакноты в таком-то количестве
    private void requiredBankNote(BankNote bankNote, long getAmount){
        for (Cassette cassette: cassetteList){
            if(cassette.getBankNote() == bankNote){
                cassette.requiredMoney(getAmount);
            }
        }
    }
    //================================================
    // кол-во банкнот в банкомате
    private long getAmountBankNote(BankNote bankNote){
        long result = 0;
        for (Cassette cassette: cassetteList){
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
        for (Cassette cell: cassetteList){
            if(cell.getBankNote() == bankNote){
                cell.putMoney(putAmount);
            }
        }
    }
}
