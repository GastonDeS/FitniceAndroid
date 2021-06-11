package com.example.fitnice.viewmodel;

import androidx.lifecycle.ViewModel;

public class DisplayViewModel extends ViewModel {

    private String value = "cabedes";

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
