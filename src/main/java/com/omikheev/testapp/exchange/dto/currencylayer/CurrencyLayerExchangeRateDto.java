package com.omikheev.testapp.exchange.dto.currencylayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * CurrencyLayer api exchange rate dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class CurrencyLayerExchangeRateDto {

    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;
}
