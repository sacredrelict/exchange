package com.omikheev.testapp.exchange;

import com.omikheev.testapp.exchange.builder.CurrencyBuilder;
import com.omikheev.testapp.exchange.entity.Currency;

/**
 * Test utils
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public final class TestUtils {

    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final Currency SOURCE_CURRENCY = CurrencyBuilder.buildCurrency(1L, TestUtils.USD);
    public static final Currency TARGET_CURRENCY = CurrencyBuilder.buildCurrency(2L, TestUtils.EUR);

}
