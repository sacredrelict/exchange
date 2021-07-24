package com.omikheev.testapp.exchange.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Conversation list request dto
 *
 * @author Oleg Mikheev
 * @since 23.07.2021
 */
@Data
@AllArgsConstructor
@ApiModel
public class ConversionListRequest {

    @ApiModelProperty(value = "transaction id", example = "1")
    private long transactionId;

    @ApiModelProperty(value = "transaction date", example = "2021-07-24T16:47:21")
    private LocalDateTime transactionDate;
}
