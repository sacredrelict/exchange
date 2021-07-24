package com.omikheev.testapp.exchange.controller;

import com.omikheev.testapp.exchange.dto.request.ConversionRequest;
import com.omikheev.testapp.exchange.dto.response.ConversionResponse;
import com.omikheev.testapp.exchange.service.TransactionService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Controller for transactions
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@RestController
@RequestMapping(path = "/api/v1/transaction")
@Api(value = "transaction", description = "Operations with transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = requireNonNull(transactionService, "transactionService");
    }

    @ApiOperation("Create conversion")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "OK", response = ConversionResponse.class),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Conversion creating error"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request")
    })
    @PostMapping("/conversion")
    public ConversionResponse createConversion(@RequestBody @Valid @NotNull ConversionRequest request) {
        return transactionService.createConversion(request);
    }

    @ApiOperation("Get conversion")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", response = ConversionResponse.class)
    })
    @GetMapping(path = "/conversion/{id}")
    public ConversionResponse getTransactionById(@ApiParam(value = "id", defaultValue = "1") @PathVariable("id") @NotBlank Long id) {
        return transactionService.getTransactionById(id);
    }

    @ApiOperation("Get conversions")
    @ApiResponses({
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK", responseContainer = "Page", response = ConversionResponse.class)
    })
    @GetMapping(path = "/conversions")
    public Page<ConversionResponse> getAllByTransactionDate(@RequestParam(value = "transactionDate", defaultValue = "2021-07-24T16:47:21")
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime transactionDate,
                                                            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                                                            @RequestParam(value = "sort", defaultValue = "createdAt", required = false) String sort) {
        return transactionService.getAllByTransactionDate(transactionDate, PageRequest.of(page, size, DESC, sort));
    }
}
