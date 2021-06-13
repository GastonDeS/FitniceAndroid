package com.example.fitnice;

import java.util.ArrayList;

public class FilterOptions {

    String filterCatName;
    ArrayList<String> filterOptionsItemsNames;

    public FilterOptions(String filterCatName, ArrayList<String> filterOptionsItemsNames) {
        this.filterCatName = filterCatName;
        this.filterOptionsItemsNames = filterOptionsItemsNames;
    }
}
