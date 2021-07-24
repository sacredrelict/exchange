package com.omikheev.testapp.exchange.service;

import com.omikheev.testapp.exchange.TestUtils;
import com.omikheev.testapp.exchange.builder.CurrencyBuilder;
import com.omikheev.testapp.exchange.client.CurrencyLayerClient;
import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import com.omikheev.testapp.exchange.dto.currencylayer.CurrencyLayerExchangeRateDto;
import com.omikheev.testapp.exchange.dto.request.ExchangeRateRequest;
import com.omikheev.testapp.exchange.dto.response.ExchangeRateResponse;
import com.omikheev.testapp.exchange.entity.Currency;
import com.omikheev.testapp.exchange.exception.EntityNotFoundException;
import com.omikheev.testapp.exchange.repository.CurrencyRepository;
import com.omikheev.testapp.exchange.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Test {@link CurrencyService}
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    private final CurrencyRepository currencyRepository = Mockito.mock(CurrencyRepository.class);
    private final CurrencyLayerClient currencyLayerClient = Mockito.mock(CurrencyLayerClient.class);
    private CurrencyService currencyService;

    @BeforeEach
    void init() {
        ModelMapper modelMapper = new ModelMapper();
        currencyService = new CurrencyServiceImpl(currencyRepository, currencyLayerClient, modelMapper);
    }

    @Test
    void testGetCurrencyList() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(CurrencyBuilder.buildCurrency(1L, TestUtils.USD));
        currencies.add(CurrencyBuilder.buildCurrency(2L, TestUtils.EUR));
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<CurrencyDto> dtos = currencyService.getCurrencyList();

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
    }

    @Test
    void testGetExchangeRate() {
        double rate = 0.8943;
        String pair = TestUtils.USD + TestUtils.EUR;
        when(currencyRepository.getCurrencyByIsoCode(TestUtils.USD)).thenReturn(TestUtils.SOURCE_CURRENCY);
        when(currencyRepository.getCurrencyByIsoCode(TestUtils.EUR)).thenReturn(TestUtils.TARGET_CURRENCY);

        Map<String, Double> quotes = new LinkedHashMap<>();
        quotes.put(pair, rate);
        CurrencyLayerExchangeRateDto currencyLayerExchangeRateDto = new CurrencyLayerExchangeRateDto(
                true,
                "terms",
                "privacy",
                System.currentTimeMillis(),
                pair,
                quotes
        );
        when(currencyLayerClient.getExchangeRate(TestUtils.USD, TestUtils.EUR)).thenReturn(currencyLayerExchangeRateDto);

        ExchangeRateRequest request = new ExchangeRateRequest(TestUtils.USD, TestUtils.EUR);
        ExchangeRateResponse response = currencyService.getExchangeRate(request);

        assertNotNull(response);
        assertEquals(rate, response.getRate());
    }

    @Test
    void testGetExchangeRate_currencyNotFound() {
        ExchangeRateRequest request = new ExchangeRateRequest("Fake", TestUtils.EUR);
        assertThrows(
                EntityNotFoundException.class,
                () -> currencyService.getExchangeRate(request),
                "Currency Fake not found in the database"
        );
    }
}
