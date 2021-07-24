package com.omikheev.testapp.exchange.dto.transaction;

import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Transaction dto
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class TransactionDto {

    private Long id;
    private Double amount;
    private LocalDateTime createdAt;
    private CurrencyDto currencySource;
    private CurrencyDto currencyTarget;
}
