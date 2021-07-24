package com.omikheev.testapp.exchange.service.impl;

import com.omikheev.testapp.exchange.dto.currency.CurrencyDto;
import com.omikheev.testapp.exchange.dto.request.ConversionRequest;
import com.omikheev.testapp.exchange.dto.response.ConversionResponse;
import com.omikheev.testapp.exchange.dto.transaction.TransactionDto;
import com.omikheev.testapp.exchange.entity.Currency;
import com.omikheev.testapp.exchange.entity.Transaction;
import com.omikheev.testapp.exchange.exception.EntityNotFoundException;
import com.omikheev.testapp.exchange.exception.RepositoryException;
import com.omikheev.testapp.exchange.repository.TransactionRepository;
import com.omikheev.testapp.exchange.service.CurrencyService;
import com.omikheev.testapp.exchange.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Transaction service layer implementation
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final CurrencyService currencyService;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public TransactionServiceImpl(CurrencyService currencyService,
                                  TransactionRepository transactionRepository,
                                  ModelMapper modelMapper) {
        this.currencyService = requireNonNull(currencyService, "currencyService");
        this.transactionRepository = requireNonNull(transactionRepository, "transactionRepository");
        this.modelMapper = requireNonNull(modelMapper, "modelMapper");
    }

    @Override
    public ConversionResponse createConversion(ConversionRequest request) {
        Currency sourceCurrency = currencyService.checkCurrency(request.getCurrencySource());
        Currency targetCurrency = currencyService.checkCurrency(request.getCurrencyTarget());

        TransactionDto dto = new TransactionDto();
        dto.setAmount(request.getAmount());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setCurrencySource(modelMapper.map(sourceCurrency, CurrencyDto.class));
        dto.setCurrencyTarget(modelMapper.map(targetCurrency, CurrencyDto.class));

        try {
            Transaction transaction = transactionRepository.save(modelMapper.map(dto, Transaction.class));
            return new ConversionResponse(transaction.getAmount(), transaction.getId());
        } catch (Exception ex) {
            LOG.error("Error during saving entity", ex);
            throw new RepositoryException("Error during saving device into database. Details: " + ex.getMessage());
        }
    }

    @Override
    public ConversionResponse getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction with id " + id + " not found in the database");
        }
        return new ConversionResponse(transaction.get().getAmount(), transaction.get().getId());
    }

    @Override
    public Page<ConversionResponse> getAllByTransactionDate(LocalDateTime createdAt, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAllByCreatedAtBetween(createdAt, LocalDateTime.now(), pageable);
        List<ConversionResponse> conversions = transactions
                .stream()
                .map(transaction -> new ConversionResponse(transaction.getAmount(), transaction.getId()))
                .collect(Collectors.toList());

        return new PageImpl<>(conversions, pageable, transactions.getTotalElements());
    }
}
