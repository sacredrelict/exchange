package com.omikheev.testapp.exchange.service;

import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import com.omikheev.testapp.exchange.dto.request.ExchangeRateRequest;
import com.omikheev.testapp.exchange.dto.response.ExchangeRateResponse;
import com.omikheev.testapp.exchange.entity.Currency;

import java.util.List;

/**
 * Base interface for Currency service layer
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public interface CurrencyService {

    /**
     * find all currencies
     *
     * @return list of {@link CurrencyDto}
     */
    List<CurrencyDto> getCurrencyList();

    /**
     * find exchange rate for the currency pair
     *
     * @param request   request dto
     * @return {@link ExchangeRateResponse}
     */
    ExchangeRateResponse getExchangeRate(ExchangeRateRequest request);

    /**
     * check currency is existing
     *
     * @param isoCode   currency iso code
     * @return {@link Currency}
     */
    Currency checkCurrency(String isoCode);
}
