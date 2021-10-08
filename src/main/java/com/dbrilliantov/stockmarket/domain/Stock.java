package com.dbrilliantov.stockmarket.domain;

import javax.persistence.*;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private String stock;

    @Column(nullable = false)
    private int count;

    public Stock() {
    }

    public Stock(Account account, String stock, int count) {
        this.account = account;
        this.stock = stock;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}