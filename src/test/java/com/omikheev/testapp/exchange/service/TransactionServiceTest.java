package com.omikheev.testapp.exchange.service;

import com.omikheev.testapp.exchange.TestUtils;
import com.omikheev.testapp.exchange.builder.TransactionBuilder;
import com.omikheev.testapp.exchange.dto.request.ConversionRequest;
import com.omikheev.testapp.exchange.dto.response.ConversionResponse;
import com.omikheev.testapp.exchange.entity.Transaction;
import com.omikheev.testapp.exchange.exception.EntityNotFoundException;
import com.omikheev.testapp.exchange.repository.TransactionRepository;
import com.omikheev.testapp.exchange.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Test {@link TransactionService}
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private final CurrencyService currencyService = Mockito.mock(CurrencyService.class);
    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private TransactionService transactionService;

    @BeforeEach
    void init() {
        ModelMapper modelMapper = new ModelMapper();
        transactionService = new TransactionServiceImpl(currencyService, transactionRepository, modelMapper);
    }

    @Test
    void testCreateConversion() {
        when(currencyService.checkCurrency(TestUtils.USD)).thenReturn(TestUtils.SOURCE_CURRENCY);
        when(currencyService.checkCurrency(TestUtils.EUR)).thenReturn(TestUtils.TARGET_CURRENCY);

        Transaction transaction = TransactionBuilder.buildTransaction(
                1L,
                10.0,
                LocalDateTime.now(),
                TestUtils.SOURCE_CURRENCY,
                TestUtils.TARGET_CURRENCY
        );
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        ConversionRequest request = new ConversionRequest(TestUtils.USD, TestUtils.EUR, 10.0);
        ConversionResponse response = transactionService.createConversion(request);

        assertNotNull(response);
        assertEquals(10.0, response.getAmount());
        assertEquals(1L, response.getTransactionId());
    }

    @Test
    void testTransactionById() {
        Transaction transaction = TransactionBuilder.buildTransaction(
                1L,
                10.0,
                LocalDateTime.now(),
                TestUtils.SOURCE_CURRENCY,
                TestUtils.TARGET_CURRENCY
        );
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

        ConversionResponse response = transactionService.getTransactionById(1L);

        assertNotNull(response);
        assertEquals(10.0, response.getAmount());
        assertEquals(1L, response.getTransactionId());
    }

    @Test
    void testTransactionById_notFound() {
        when(transactionRepository.getById(anyLong())).thenReturn(null);

        assertThrows(
                EntityNotFoundException.class,
                () -> transactionService.getTransactionById(1L),
                "Transaction with id 1 not found in the database"
        );
    }

    @Test
    void testGetAllByTransactionDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(TransactionBuilder.buildTransaction(
                1L, 10.0, localDateTime, TestUtils.SOURCE_CURRENCY, TestUtils.TARGET_CURRENCY)
        );
        transactionList.add(TransactionBuilder.buildTransaction(
                2L, 20.0, localDateTime, TestUtils.SOURCE_CURRENCY, TestUtils.TARGET_CURRENCY)
        );
        Pageable pageable = PageRequest.of(0, 10, DESC, "createdAt");
        Page<Transaction> transactions = new PageImpl<>(transactionList, pageable, 2);
        when(transactionRepository.findAllByCreatedAtBetween(any(), any(), any())).thenReturn(transactions);

        Page<ConversionResponse> response = transactionService.getAllByTransactionDate(localDateTime, pageable);

        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertNotNull(response.getContent());

        assertNotNull(response.getContent().get(0));
        assertEquals(10.0, response.getContent().get(0).getAmount());
        assertEquals(1L, response.getContent().get(0).getTransactionId());

        assertNotNull(response.getContent().get(1));
        assertEquals(20.0, response.getContent().get(1).getAmount());
        assertEquals(2L, response.getContent().get(1).getTransactionId());
    }
}
