package com.dbrilliantov.stockmarket.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private float sum;

    @Column(nullable = false)
    private LocalDateTime transacted;

    public Transaction() {
    }

    public Transaction(Account account, String stock, int count, TransactionType type, float sum, LocalDateTime transacted) {
        this.account = account;
        this.stock = stock;
        this.count = count;
        this.type = type;
        this.sum = sum;
        this.transacted = transacted;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public LocalDateTime getTransacted() {
        return transacted;
    }

    public void setTransacted(LocalDateTime transacted) {
        this.transacted = transacted;
    }

}