package com.omikheev.testapp.exchange.controller;

import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import com.omikheev.testapp.exchange.dto.request.ExchangeRateRequest;
import com.omikheev.testapp.exchange.dto.response.ExchangeRateResponse;
import com.omikheev.testapp.exchange.service.CurrencyService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.net.HttpURLConnection;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Controller for currencies
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@RestController
@RequestMapping(path = "/api/v1/currency")
@Api(value = "currency", description = "Operations with currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = requireNonNull(currencyService, "currencyService");
    }

    @ApiOperation("Get currency list")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", responseContainer = "List", response = CurrencyDto.class)
    })
    @GetMapping
    public List<CurrencyDto> getCurrencyList() {
        return currencyService.getCurrencyList();
    }

    @ApiOperation("Get exchange rate")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = ExchangeRateResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Currency not found")
    })
    @GetMapping("/rate/{source}/{target}")
    public ExchangeRateResponse getExchangeRate(@ApiParam(value = "source", defaultValue = "USD") @PathVariable("source") @NotBlank String source,
                                                @ApiParam(value = "source", defaultValue = "EUR") @PathVariable("target") @NotBlank String target) {
        return currencyService.getExchangeRate(new ExchangeRateRequest(source, target));
    }
}
