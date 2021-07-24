package com.omikheev.testapp.exchange.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Currency pair dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class CurrencyPairDto {

    @ApiModelProperty(value = "currency source", example = "USD")
    @NotNull
    private String currencySource;

    @ApiModelProperty(value = "currency target", example = "EUR")
    @NotNull
    private String currencyTarget;
}
