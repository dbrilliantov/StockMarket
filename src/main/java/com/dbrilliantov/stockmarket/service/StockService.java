package com.dbrilliantov.stockmarket.service;

import com.dbrilliantov.stockmarket.domain.Stock;
import com.dbrilliantov.stockmarket.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    public Stock getStock(int account_id, String stock) {
        return stockRepository.findStockByAccount_IdAndStock(account_id, stock);
    }

    public void removeStock(Stock stock) {
        stockRepository.delete(stock);
    }

    public List<Stock> getPortfolio(int account_id) {
        return stockRepository.getAllByAccount_Id(account_id);
    }

}