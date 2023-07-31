package com.zamani;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByFromAccountId(Long fromAccountId);
    List<Transaction> findByToAccountId(Long toAccountId);

}