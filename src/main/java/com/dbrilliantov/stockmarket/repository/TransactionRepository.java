package com.dbrilliantov.stockmarket.repository;

import com.dbrilliantov.stockmarket.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> getAllByAccount_Id(int account_id);

}