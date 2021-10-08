package com.dbrilliantov.stockmarket.controller;

import com.dbrilliantov.stockmarket.api.domain.Result;
import com.dbrilliantov.stockmarket.api.service.DataService;
import com.dbrilliantov.stockmarket.domain.Account;
import com.dbrilliantov.stockmarket.domain.Stock;
import com.dbrilliantov.stockmarket.domain.Transaction;
import com.dbrilliantov.stockmarket.domain.TransactionType;
import com.dbrilliantov.stockmarket.service.StockService;
import com.dbrilliantov.stockmarket.service.TransactionService;
import com.dbrilliantov.stockmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/office")
public class MainController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String index(Model model) {

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Stock> stocks = stockService.getPortfolio(account.getId());

        if (stocks != null && !stocks.isEmpty()) {
            model.addAttribute("stocks", stocks);
            model.addAttribute("dataService", dataService);
        }

        model.addAttribute("balance", account.getBalance());
        return "/office/index";
    }

    @GetMapping("/query")
    public String query() {
        return "/office/query";
    }

    @PostMapping("/query")
    public String query(Model model, @RequestParam("stock") String stock) {

        Result result = dataService.getStock(stock);

        if (result != null) {
            model.addAttribute("result", "The price of 1 share " + result.getCompanyName() + " is $" + result.getLatestPrice());
        } else {
            model.addAttribute("result", "Failed to get data about this stock");
        }

        return "/office/query";
    }

    @GetMapping("/buy")
    public String buy() {
        return "/office/buy";
    }

    @PostMapping("/buy")
    public String buy(Model model, @RequestParam("stock") String stock, @RequestParam("count") int count) {

        stock = stock.toUpperCase();

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Stock portfolio = stockService.getStock(account.getId(), stock);
        Result result = dataService.getStock(stock);

        if (result == null) {
            model.addAttribute("result", "Failed to get data about this stock");
            return "/office/buy";
        }

        if (count < 0 || count > (int) account.getBalance() / result.getLatestPrice()) {
            model.addAttribute("result", "You cannot buy that many shares.");
            return "/office/buy";
        }

        if (portfolio == null) {
            portfolio = new Stock(account, stock, count);
        } else {
            portfolio.setCount(portfolio.getCount() + count);
        }

        float sum = count * result.getLatestPrice();

        account.setBalance(account.getBalance() - sum);
        userService.saveAccount(account);

        stockService.saveStock(portfolio);
        transactionService.saveTransaction(new Transaction(account, stock, count, TransactionType.BUY, sum, LocalDateTime.now()));

        model.addAttribute("result", "You bought " + count + " shares of " + result.getCompanyName() + " for $" + sum);
        return "/office/buy";
    }

    @GetMapping("/sell")
    public String sell() {
        return "/office/sell";
    }

    @PostMapping("/sell")
    public String sell(Model model, @RequestParam("stock") String stock, @RequestParam("count") int count) {

        stock = stock.toUpperCase();

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Stock portfolio = stockService.getStock(account.getId(), stock);
        Result result = dataService.getStock(stock);

        if (result == null || portfolio == null) {
            model.addAttribute("result", "Failed to get data about this stock");
            return "/office/sell";
        }

        if (count < 0 || count > portfolio.getCount()) {
            model.addAttribute("result", "You cannot sell that many shares.");
            return "/office/sell";
        }

        if (portfolio.getCount() - count != 0) {
            portfolio.setCount(portfolio.getCount() - count);
            stockService.saveStock(portfolio);
        } else {
            stockService.removeStock(portfolio);
        }

        float sum = count * result.getLatestPrice();

        account.setBalance(account.getBalance() + sum);
        userService.saveAccount(account);

        transactionService.saveTransaction(new Transaction(account, stock, count, TransactionType.SELL, sum, LocalDateTime.now()));

        model.addAttribute("result", "You sold " + count + " shares of " + result.getCompanyName() + " for $" + sum);
        return "/office/sell";
    }

    @GetMapping("transactions")
    public String transactions(Model model) {

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> transactions = transactionService.getTransactions(account.getId());

        if (transactions != null && !transactions.isEmpty()) {
            model.addAttribute("transactions", transactions);
        }

        return "/office/transactions";
    }

}