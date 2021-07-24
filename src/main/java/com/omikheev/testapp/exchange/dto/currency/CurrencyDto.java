package com.omikheev.testapp.exchange.dto.currency;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Currency dto
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class CurrencyDto {

    private long id;
    private String isoCode;
}
