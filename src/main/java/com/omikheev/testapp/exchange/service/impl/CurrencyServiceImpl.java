package com.omikheev.testapp.exchange.service.impl;

import com.omikheev.testapp.exchange.client.CurrencyLayerClient;
import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import com.omikheev.testapp.exchange.dto.currencylayer.CurrencyLayerExchangeRateDto;
import com.omikheev.testapp.exchange.dto.request.ExchangeRateRequest;
import com.omikheev.testapp.exchange.dto.response.ExchangeRateResponse;
import com.omikheev.testapp.exchange.entity.Currency;
import com.omikheev.testapp.exchange.exception.EntityNotFoundException;
import com.omikheev.testapp.exchange.repository.CurrencyRepository;
import com.omikheev.testapp.exchange.service.CurrencyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Currency service layer implementation
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;
    private final CurrencyLayerClient currencyLayerClient;
    private final ModelMapper modelMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository,
                               CurrencyLayerClient currencyLayerClient,
                               ModelMapper modelMapper) {
        this.currencyRepository = requireNonNull(currencyRepository, "currencyRepository");
        this.currencyLayerClient = requireNonNull(currencyLayerClient, "currencyLayerClient");
        this.modelMapper = requireNonNull(modelMapper, "modelMapper");
    }

    @Override
    public List<CurrencyDto> getCurrencyList() {
        List<Currency> currencies = currencyRepository.findAll();

        return currencies
                .stream()
                .map(currency -> modelMapper.map(currency, CurrencyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExchangeRateResponse getExchangeRate(ExchangeRateRequest request) {
        checkCurrency(request.getCurrencySource());
        checkCurrency(request.getCurrencyTarget());

        CurrencyLayerExchangeRateDto exchangeRate =
                currencyLayerClient.getExchangeRate(request.getCurrencySource(), request.getCurrencyTarget());

        String pair = request.getCurrencySource() + request.getCurrencyTarget();
        if (exchangeRate.getQuotes() == null || exchangeRate.getQuotes().get(pair) == null) {
            LOG.error("Rate not found for currency pair {}", pair);
        }
        double rate = exchangeRate.getQuotes().get(pair);

        return new ExchangeRateResponse(request.getCurrencySource(), request.getCurrencyTarget(), rate);
    }

    @Override
    public Currency checkCurrency(String isoCode) {
        Currency currency = currencyRepository.getCurrencyByIsoCode(isoCode);
        if (currency == null) {
            throw new EntityNotFoundException("Currency " + isoCode + " not found in the database");
        }
        return currency;
    }
}
