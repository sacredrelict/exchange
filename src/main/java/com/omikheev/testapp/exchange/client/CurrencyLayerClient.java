package com.omikheev.testapp.exchange.client;

import com.omikheev.testapp.exchange.dto.currencylayer.CurrencyLayerExchangeRateDto;

import javax.validation.constraints.NotNull;

/**
 * CurrencyLayer client
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public interface CurrencyLayerClient {

    /**
     * Get exchange rate for currencies
     *
     * @param currencySource   currency source
     * @param currencyTarget   currency target
     * @return {@link CurrencyLayerExchangeRateDto}
     */
    CurrencyLayerExchangeRateDto getExchangeRate(@NotNull String currencySource, @NotNull String currencyTarget);
}
