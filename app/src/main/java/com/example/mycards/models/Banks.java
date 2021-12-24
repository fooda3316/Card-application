package com.example.mycards.models;

import java.util.List;

public class Banks {
    private List<Bank> banks;

    public Banks(List<Bank> banks) {
        this.banks = banks;
    }

    public List<Bank> getBanks() {
        return banks;
    }
}
