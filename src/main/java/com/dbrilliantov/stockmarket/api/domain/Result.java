package com.dbrilliantov.stockmarket.api.domain;

public class Result {

    private String symbol;
    private String companyName;
    private float latestPrice;

    public Result() {
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public float getLatestPrice() {
        return latestPrice;
    }

}