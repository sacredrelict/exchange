package com.omikheev.testapp.exchange.service;

import com.omikheev.testapp.exchange.dto.request.ConversionRequest;
import com.omikheev.testapp.exchange.dto.response.ConversionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Base interface for Transaction service layer
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
public interface TransactionService {

    /**
     * create conversation
     *
     * @param request   request dto
     * @return {@link ConversionResponse}
     */
    ConversionResponse createConversion(ConversionRequest request);

    /**
     * get transaction by id
     *
     * @param id   transaction id
     * @return {@link ConversionResponse}
     */
    ConversionResponse getTransactionById(Long id);

    /**
     * get all transactions from date till now
     *
     * @param from       from date
     * @param pageable   pageable object
     * @return page of {@link ConversionResponse}
     */
    Page<ConversionResponse> getAllByTransactionDate(LocalDateTime from, Pageable pageable);
}
