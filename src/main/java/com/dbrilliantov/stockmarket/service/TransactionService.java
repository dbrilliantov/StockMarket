package com.dbrilliantov.stockmarket.service;

import com.dbrilliantov.stockmarket.domain.Transaction;
import com.dbrilliantov.stockmarket.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(int account_id) {
        return transactionRepository.getAllByAccount_Id(account_id);
    }

}