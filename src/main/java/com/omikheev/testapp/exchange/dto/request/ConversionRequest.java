package com.omikheev.testapp.exchange.dto.request;

import com.omikheev.testapp.exchange.dto.CurrencyPairDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Conversation request dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class ConversionRequest extends CurrencyPairDto {

    @ApiModelProperty(value = "amount", example = "10")
    @NotNull
    private double amount;

    public ConversionRequest(String currencySource,
                             String currencyTarget,
                             double amount) {
        super(currencySource, currencyTarget);
        this.amount = amount;
    }
}
