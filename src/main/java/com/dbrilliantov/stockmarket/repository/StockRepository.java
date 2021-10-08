package com.dbrilliantov.stockmarket.repository;

import com.dbrilliantov.stockmarket.domain.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Integer> {

    List<Stock> getAllByAccount_Id(int account_id);

    Stock findStockByAccount_IdAndStock(int account_id, String stock);

}