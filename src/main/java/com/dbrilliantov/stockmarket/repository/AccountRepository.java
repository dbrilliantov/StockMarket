package com.dbrilliantov.stockmarket.repository;

import com.dbrilliantov.stockmarket.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findAccountByUsername(String username);

}