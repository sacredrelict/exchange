package com.omikheev.testapp.exchange.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omikheev.testapp.exchange.dto.CurrencyPairDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Exchange rte response
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class ExchangeRateResponse extends CurrencyPairDto {

    private double rate;

    public ExchangeRateResponse(String currencySource,
                                String currencyTarget,
                                double rate) {
        super(currencySource, currencyTarget);
        this.rate = rate;
    }
}
