package com.omikheev.testapp.exchange.client.impl;

import com.omikheev.testapp.exchange.client.CurrencyLayerClient;
import com.omikheev.testapp.exchange.configuration.CurrencyLayerProperties;
import com.omikheev.testapp.exchange.dto.currencylayer.CurrencyLayerExchangeRateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

/**
 * CurrencyLayer client implementation
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Component
public class CurrencyLayerClientImpl implements CurrencyLayerClient {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyLayerClientImpl.class);

    private RestTemplate restTemplate;
    private CurrencyLayerProperties currencyLayerProperties;

    public CurrencyLayerClientImpl(RestTemplateBuilder builder,
                                   CurrencyLayerProperties currencyLayerProperties) {
        this.restTemplate = builder.build();
        this.currencyLayerProperties = currencyLayerProperties;
    }

    @Override
    public CurrencyLayerExchangeRateDto getExchangeRate(@NotNull String currencySource, @NotNull String currencyTarget) {
        String url = currencyLayerProperties.getApiUrl() + currencyLayerProperties.getApiRate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_key", currencyLayerProperties.getApiKey())
                .queryParam("currencies", currencyTarget)
                .queryParam("source", currencySource)
                .queryParam("format", "1");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<CurrencyLayerExchangeRateDto> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                CurrencyLayerExchangeRateDto.class);

        if (response.getBody() == null || !response.hasBody()) {
            LOG.debug("No response for source currency {} and target currency {}, please, try again",
                    currencySource,
                    currencyTarget);
            return null;
        }

        return response.getBody();
    }
}
