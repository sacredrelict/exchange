package com.omikheev.testapp.exchange.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration for currencylayer
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Component
@ConfigurationProperties(prefix = "currencylayer")
@Data
public class CurrencyLayerProperties {

    private String apiKey;
    private String apiUrl;
    private String apiRate;
}
