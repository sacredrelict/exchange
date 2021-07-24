package com.omikheev.testapp.exchange.builder;

import com.omikheev.testapp.exchange.entity.Currency;
import com.omikheev.testapp.exchange.entity.Transaction;

import java.time.LocalDateTime;

/**
 * Builder for Transaction
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public final class TransactionBuilder {

    public static Transaction buildTransaction(Long id,
                                               Double amount,
                                               LocalDateTime createdAt,
                                               Currency currencySource,
                                               Currency currencyTarget) {
        return new Transaction(id, amount, createdAt, currencySource, currencyTarget);
    }
}
