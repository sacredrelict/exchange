package com.omikheev.testapp.exchange.dto.request;

import com.omikheev.testapp.exchange.dto.CurrencyPairDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exchange rate request dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class ExchangeRateRequest extends CurrencyPairDto {

    public ExchangeRateRequest(String currencySource,
                               String currencyTarget) {
        super(currencySource, currencyTarget);
    }
}
