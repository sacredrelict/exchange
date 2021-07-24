package com.omikheev.testapp.exchange.repository;

import com.omikheev.testapp.exchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Base repository interface for Currency entity
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    /**
     * Get currency by the code
     *
     * @param isoCode   iso code
     * @return   {@link Currency}
     */
    Currency getCurrencyByIsoCode(String isoCode);
}
