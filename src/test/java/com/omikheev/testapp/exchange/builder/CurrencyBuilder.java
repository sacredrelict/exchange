package com.omikheev.testapp.exchange.builder;

import com.omikheev.testapp.exchange.entity.Currency;

/**
 * Builder for Currency
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public final class CurrencyBuilder {

    public static Currency buildCurrency(Long id, String isoCode) {
        return new Currency(id, isoCode);
    }
}
