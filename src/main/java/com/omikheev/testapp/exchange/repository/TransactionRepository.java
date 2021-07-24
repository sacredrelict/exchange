package com.omikheev.testapp.exchange.repository;

import com.omikheev.testapp.exchange.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Base repository interface for Transaction entity
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * find all transactions from created date till to date
     *
     * @param from       from date
     * @param to         to date
     * @param pageable   pageable object
     * @return page of {@link Transaction}
     */
    Page<Transaction> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
