package com.omikheev.testapp.exchange.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Conversation response dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class ConversionResponse {

    private double amount;
    private long transactionId;
}
